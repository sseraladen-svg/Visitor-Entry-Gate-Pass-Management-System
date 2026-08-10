import { useState } from 'react';
import client, { errorMessage } from '../api/client.js';
import StatusBadge from '../components/StatusBadge.jsx';
import { useAuth } from '../context/AuthContext.jsx';

const formatDateTime = (value) => (value ? new Date(value).toLocaleString() : '—');

export default function Verify() {
  const { hasRole } = useAuth();
  const [code, setCode] = useState('');
  const [pass, setPass] = useState(null);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const lookup = async (event) => {
    event.preventDefault();
    setError('');
    setMessage('');
    setPass(null);
    try {
      const { data } = await client.get(`/gate-passes/code/${code.trim()}`);
      setPass(data);
    } catch (err) {
      setError(errorMessage(err, 'Pass not found'));
    }
  };

  const act = async (action) => {
    setError('');
    setMessage('');
    try {
      const { data } = await client.post(`/gate-passes/code/${pass.passCode}/${action}`);
      setPass(data);
      setMessage(action === 'check-in' ? 'Visitor checked in' : 'Visitor checked out');
    } catch (err) {
      setError(errorMessage(err, `Unable to ${action}`));
    }
  };

  return (
    <div>
      <h2>Verify pass at the gate</h2>
      <form className="inline-form" onSubmit={lookup}>
        <input
          placeholder="Enter pass code e.g. VP-AB12CD34"
          value={code}
          onChange={(event) => setCode(event.target.value.toUpperCase())}
          required
        />
        <button type="submit">Verify</button>
      </form>

      {error && <div className="alert error">{error}</div>}
      {message && <div className="alert success">{message}</div>}

      {pass && (
        <div className="card pass-card">
          <div className="page-head">
            <h3>{pass.passCode}</h3>
            <StatusBadge status={pass.status} />
          </div>
          <dl>
            <div>
              <dt>Visitor</dt>
              <dd>
                {pass.visitor.fullName} ({pass.visitor.phone})
              </dd>
            </div>
            <div>
              <dt>ID proof</dt>
              <dd>
                {pass.visitor.idProofType || '—'} {pass.visitor.idProofNumber || ''}
              </dd>
            </div>
            <div>
              <dt>Host</dt>
              <dd>
                {pass.host.fullName} — {pass.host.department || 'Staff'}
              </dd>
            </div>
            <div>
              <dt>Purpose</dt>
              <dd>{pass.purpose}</dd>
            </div>
            <div>
              <dt>Valid window</dt>
              <dd>
                {formatDateTime(pass.expectedEntry)} → {formatDateTime(pass.expectedExit)}
              </dd>
            </div>
            <div>
              <dt>Vehicle</dt>
              <dd>{pass.vehicleNumber || '—'}</dd>
            </div>
            <div>
              <dt>Checked in</dt>
              <dd>{formatDateTime(pass.checkInTime)}</dd>
            </div>
            <div>
              <dt>Checked out</dt>
              <dd>{formatDateTime(pass.checkOutTime)}</dd>
            </div>
          </dl>

          {hasRole('ADMIN', 'SECURITY') && (
            <div className="actions">
              <button type="button" disabled={pass.status !== 'APPROVED'} onClick={() => act('check-in')}>
                Check in
              </button>
              <button
                type="button"
                className="secondary"
                disabled={pass.status !== 'CHECKED_IN'}
                onClick={() => act('check-out')}
              >
                Check out
              </button>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
