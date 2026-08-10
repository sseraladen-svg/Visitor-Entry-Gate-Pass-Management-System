import { useCallback, useEffect, useState } from 'react';
import client, { errorMessage } from '../api/client.js';
import StatusBadge from '../components/StatusBadge.jsx';
import { useAuth } from '../context/AuthContext.jsx';

const statuses = ['', 'PENDING', 'APPROVED', 'REJECTED', 'CHECKED_IN', 'CHECKED_OUT', 'CANCELLED'];

const formatDateTime = (value) => (value ? new Date(value).toLocaleString() : '—');

export default function GatePasses() {
  const { hasRole } = useAuth();
  const [passes, setPasses] = useState([]);
  const [status, setStatus] = useState('');
  const [error, setError] = useState('');

  const load = useCallback(() => {
    client
      .get('/gate-passes', { params: status ? { status } : {} })
      .then((response) => setPasses(response.data))
      .catch((err) => setError(errorMessage(err, 'Unable to load gate passes')));
  }, [status]);

  useEffect(() => {
    load();
  }, [load]);

  const act = async (id, action) => {
    setError('');
    try {
      await client.post(`/gate-passes/${id}/${action}`);
      load();
    } catch (err) {
      setError(errorMessage(err, `Unable to ${action} the pass`));
    }
  };

  return (
    <div>
      <div className="page-head">
        <h2>Gate passes</h2>
        <select value={status} onChange={(event) => setStatus(event.target.value)}>
          {statuses.map((value) => (
            <option key={value || 'all'} value={value}>
              {value ? value.replace('_', ' ') : 'All statuses'}
            </option>
          ))}
        </select>
      </div>

      {error && <div className="alert error">{error}</div>}

      <div className="card">
        <table>
          <thead>
            <tr>
              <th>Pass code</th>
              <th>Visitor</th>
              <th>Host</th>
              <th>Purpose</th>
              <th>Valid window</th>
              <th>Check-in / out</th>
              <th>Status</th>
              {hasRole('ADMIN', 'HOST') && <th>Actions</th>}
            </tr>
          </thead>
          <tbody>
            {passes.map((pass) => (
              <tr key={pass.id}>
                <td>{pass.passCode}</td>
                <td>
                  {pass.visitor.fullName}
                  <div className="muted small">{pass.visitor.phone}</div>
                </td>
                <td>{pass.host.fullName}</td>
                <td>{pass.purpose}</td>
                <td className="small">
                  {formatDateTime(pass.expectedEntry)}
                  <div className="muted">to {formatDateTime(pass.expectedExit)}</div>
                </td>
                <td className="small">
                  {formatDateTime(pass.checkInTime)}
                  <div className="muted">{formatDateTime(pass.checkOutTime)}</div>
                </td>
                <td>
                  <StatusBadge status={pass.status} />
                </td>
                {hasRole('ADMIN', 'HOST') && (
                  <td className="actions">
                    {pass.status === 'PENDING' && (
                      <>
                        <button type="button" onClick={() => act(pass.id, 'approve')}>
                          Approve
                        </button>
                        <button type="button" className="secondary" onClick={() => act(pass.id, 'reject')}>
                          Reject
                        </button>
                      </>
                    )}
                    {pass.status === 'APPROVED' && (
                      <button type="button" className="secondary" onClick={() => act(pass.id, 'cancel')}>
                        Cancel
                      </button>
                    )}
                  </td>
                )}
              </tr>
            ))}
            {passes.length === 0 && (
              <tr>
                <td colSpan={8} className="muted">
                  No gate passes for this filter.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
