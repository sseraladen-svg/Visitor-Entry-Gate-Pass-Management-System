import { useEffect, useState } from 'react';
import client, { errorMessage } from '../api/client.js';

const initialForm = {
  visitorId: '',
  fullName: '',
  phone: '',
  email: '',
  company: '',
  idProofType: 'Aadhaar',
  idProofNumber: '',
  hostId: '',
  purpose: '',
  expectedEntry: '',
  expectedExit: '',
  vehicleNumber: '',
  numberOfVisitors: 1,
};

export default function NewGatePass() {
  const [hosts, setHosts] = useState([]);
  const [visitors, setVisitors] = useState([]);
  const [form, setForm] = useState(initialForm);
  const [created, setCreated] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    Promise.all([client.get('/users/hosts'), client.get('/visitors')])
      .then(([hostsResponse, visitorsResponse]) => {
        setHosts(hostsResponse.data);
        setVisitors(visitorsResponse.data);
        setForm((current) => ({ ...current, hostId: hostsResponse.data[0]?.id ?? '' }));
      })
      .catch((err) => setError(errorMessage(err, 'Unable to load form data')));
  }, []);

  const handleChange = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    setCreated(null);

    const payload = {
      hostId: Number(form.hostId),
      purpose: form.purpose,
      expectedEntry: form.expectedEntry,
      expectedExit: form.expectedExit,
      vehicleNumber: form.vehicleNumber || null,
      numberOfVisitors: Number(form.numberOfVisitors) || 1,
    };

    if (form.visitorId) {
      payload.visitorId = Number(form.visitorId);
    } else {
      payload.visitor = {
        fullName: form.fullName,
        phone: form.phone,
        email: form.email || null,
        company: form.company || null,
        idProofType: form.idProofType,
        idProofNumber: form.idProofNumber || null,
      };
    }

    try {
      const { data } = await client.post('/gate-passes', payload);
      setCreated(data);
      setForm({ ...initialForm, hostId: form.hostId });
    } catch (err) {
      setError(errorMessage(err, 'Unable to create gate pass'));
    }
  };

  return (
    <div>
      <h2>New gate pass</h2>
      {error && <div className="alert error">{error}</div>}
      {created && (
        <div className="alert success">
          Pass <strong>{created.passCode}</strong> created for {created.visitor.fullName} — awaiting approval.
        </div>
      )}

      <form className="card form-grid" onSubmit={handleSubmit}>
        <div className="field">
          <label htmlFor="visitorId">Existing visitor</label>
          <select id="visitorId" name="visitorId" value={form.visitorId} onChange={handleChange}>
            <option value="">— Register a new visitor —</option>
            {visitors.map((visitor) => (
              <option key={visitor.id} value={visitor.id}>
                {visitor.fullName} ({visitor.phone})
              </option>
            ))}
          </select>
        </div>

        {!form.visitorId && (
          <>
            <div className="field">
              <label htmlFor="fullName">Visitor name</label>
              <input id="fullName" name="fullName" value={form.fullName} onChange={handleChange} required />
            </div>
            <div className="field">
              <label htmlFor="phone">Visitor phone</label>
              <input id="phone" name="phone" value={form.phone} onChange={handleChange} required />
            </div>
            <div className="field">
              <label htmlFor="company">Company</label>
              <input id="company" name="company" value={form.company} onChange={handleChange} />
            </div>
            <div className="field">
              <label htmlFor="idProofNumber">ID proof number</label>
              <input id="idProofNumber" name="idProofNumber" value={form.idProofNumber} onChange={handleChange} />
            </div>
          </>
        )}

        <div className="field">
          <label htmlFor="hostId">Host</label>
          <select id="hostId" name="hostId" value={form.hostId} onChange={handleChange} required>
            {hosts.map((host) => (
              <option key={host.id} value={host.id}>
                {host.fullName} — {host.department || 'Staff'}
              </option>
            ))}
          </select>
        </div>
        <div className="field">
          <label htmlFor="purpose">Purpose of visit</label>
          <input id="purpose" name="purpose" value={form.purpose} onChange={handleChange} required />
        </div>
        <div className="field">
          <label htmlFor="expectedEntry">Expected entry</label>
          <input
            id="expectedEntry"
            name="expectedEntry"
            type="datetime-local"
            value={form.expectedEntry}
            onChange={handleChange}
            required
          />
        </div>
        <div className="field">
          <label htmlFor="expectedExit">Expected exit</label>
          <input
            id="expectedExit"
            name="expectedExit"
            type="datetime-local"
            value={form.expectedExit}
            onChange={handleChange}
            required
          />
        </div>
        <div className="field">
          <label htmlFor="vehicleNumber">Vehicle number</label>
          <input id="vehicleNumber" name="vehicleNumber" value={form.vehicleNumber} onChange={handleChange} />
        </div>
        <div className="field">
          <label htmlFor="numberOfVisitors">Number of visitors</label>
          <input
            id="numberOfVisitors"
            name="numberOfVisitors"
            type="number"
            min="1"
            value={form.numberOfVisitors}
            onChange={handleChange}
          />
        </div>
        <div className="field full">
          <button type="submit">Create gate pass</button>
        </div>
      </form>
    </div>
  );
}
