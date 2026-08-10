import { useEffect, useState } from 'react';
import client, { errorMessage } from '../api/client.js';
import { useAuth } from '../context/AuthContext.jsx';

const emptyVisitor = {
  fullName: '',
  phone: '',
  email: '',
  company: '',
  idProofType: 'Aadhaar',
  idProofNumber: '',
  address: '',
};

export default function Visitors() {
  const { hasRole } = useAuth();
  const [visitors, setVisitors] = useState([]);
  const [form, setForm] = useState(emptyVisitor);
  const [query, setQuery] = useState('');
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const load = (search = '') => {
    client
      .get('/visitors', { params: search ? { query: search } : {} })
      .then((response) => setVisitors(response.data))
      .catch((err) => setError(errorMessage(err, 'Unable to load visitors')));
  };

  useEffect(() => {
    load();
  }, []);

  const handleChange = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    setMessage('');
    try {
      await client.post('/visitors', form);
      setForm(emptyVisitor);
      setMessage('Visitor registered');
      load(query);
    } catch (err) {
      setError(errorMessage(err, 'Unable to save visitor'));
    }
  };

  const handleDelete = async (id) => {
    setError('');
    try {
      await client.delete(`/visitors/${id}`);
      load(query);
    } catch (err) {
      setError(errorMessage(err, 'Unable to delete visitor'));
    }
  };

  return (
    <div>
      <div className="page-head">
        <h2>Visitors</h2>
        <form
          className="inline-form"
          onSubmit={(event) => {
            event.preventDefault();
            load(query);
          }}
        >
          <input
            placeholder="Search by name or phone"
            value={query}
            onChange={(event) => setQuery(event.target.value)}
          />
          <button type="submit" className="secondary">
            Search
          </button>
        </form>
      </div>

      {error && <div className="alert error">{error}</div>}
      {message && <div className="alert success">{message}</div>}

      <div className="two-column">
        <form className="card" onSubmit={handleSubmit}>
          <h3>Register visitor</h3>
          <label htmlFor="fullName">Full name</label>
          <input id="fullName" name="fullName" value={form.fullName} onChange={handleChange} required />
          <label htmlFor="phone">Phone (10-15 digits)</label>
          <input id="phone" name="phone" value={form.phone} onChange={handleChange} required />
          <label htmlFor="email">Email</label>
          <input id="email" name="email" type="email" value={form.email} onChange={handleChange} />
          <label htmlFor="company">Company / Institution</label>
          <input id="company" name="company" value={form.company} onChange={handleChange} />
          <label htmlFor="idProofType">ID proof type</label>
          <select id="idProofType" name="idProofType" value={form.idProofType} onChange={handleChange}>
            <option>Aadhaar</option>
            <option>PAN</option>
            <option>Driving Licence</option>
            <option>Passport</option>
            <option>Student ID</option>
          </select>
          <label htmlFor="idProofNumber">ID proof number</label>
          <input id="idProofNumber" name="idProofNumber" value={form.idProofNumber} onChange={handleChange} />
          <label htmlFor="address">Address</label>
          <textarea id="address" name="address" rows={2} value={form.address} onChange={handleChange} />
          <button type="submit">Save visitor</button>
        </form>

        <div className="card">
          <h3>All visitors ({visitors.length})</h3>
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Phone</th>
                <th>Company</th>
                <th>ID proof</th>
                {hasRole('ADMIN') && <th />}
              </tr>
            </thead>
            <tbody>
              {visitors.map((visitor) => (
                <tr key={visitor.id}>
                  <td>{visitor.fullName}</td>
                  <td>{visitor.phone}</td>
                  <td>{visitor.company || '—'}</td>
                  <td>
                    {visitor.idProofType ? `${visitor.idProofType} ${visitor.idProofNumber || ''}` : '—'}
                  </td>
                  {hasRole('ADMIN') && (
                    <td>
                      <button type="button" className="link danger" onClick={() => handleDelete(visitor.id)}>
                        Delete
                      </button>
                    </td>
                  )}
                </tr>
              ))}
              {visitors.length === 0 && (
                <tr>
                  <td colSpan={5} className="muted">
                    No visitors found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
