import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import client, { errorMessage } from '../api/client.js';
import StatusBadge from '../components/StatusBadge.jsx';

const cards = [
  { key: 'totalVisitors', label: 'Registered visitors' },
  { key: 'totalPasses', label: 'Total passes' },
  { key: 'pendingPasses', label: 'Pending approval' },
  { key: 'approvedPasses', label: 'Approved' },
  { key: 'visitorsInsideNow', label: 'Inside campus now' },
  { key: 'checkInsToday', label: 'Check-ins today' },
];

export default function Dashboard() {
  const [stats, setStats] = useState(null);
  const [recent, setRecent] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    Promise.all([client.get('/dashboard/stats'), client.get('/gate-passes')])
      .then(([statsResponse, passesResponse]) => {
        setStats(statsResponse.data);
        setRecent(passesResponse.data.slice(0, 8));
      })
      .catch((err) => setError(errorMessage(err, 'Unable to load dashboard')));
  }, []);

  return (
    <div>
      <div className="page-head">
        <h2>Dashboard</h2>
        <Link className="button" to="/gate-passes/new">
          New gate pass
        </Link>
      </div>
      {error && <div className="alert error">{error}</div>}
      <div className="stat-grid">
        {cards.map((card) => (
          <div className="card stat" key={card.key}>
            <span className="stat-value">{stats ? stats[card.key] : '—'}</span>
            <span className="muted">{card.label}</span>
          </div>
        ))}
      </div>

      <h3>Recent gate passes</h3>
      <div className="card">
        <table>
          <thead>
            <tr>
              <th>Pass code</th>
              <th>Visitor</th>
              <th>Host</th>
              <th>Purpose</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {recent.map((pass) => (
              <tr key={pass.id}>
                <td>{pass.passCode}</td>
                <td>{pass.visitor.fullName}</td>
                <td>{pass.host.fullName}</td>
                <td>{pass.purpose}</td>
                <td>
                  <StatusBadge status={pass.status} />
                </td>
              </tr>
            ))}
            {recent.length === 0 && (
              <tr>
                <td colSpan={5} className="muted">
                  No gate passes yet.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
