import { NavLink, Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';

const links = [
  { to: '/', label: 'Dashboard', end: true },
  { to: '/gate-passes', label: 'Gate Passes' },
  { to: '/gate-passes/new', label: 'New Pass' },
  { to: '/visitors', label: 'Visitors' },
  { to: '/verify', label: 'Verify / Gate' },
];

export default function Layout() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login', { replace: true });
  };

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <h1 className="brand">Gate Pass</h1>
        <nav>
          {links.map((link) => (
            <NavLink
              key={link.to}
              to={link.to}
              end={link.end}
              className={({ isActive }) => (isActive ? 'nav-link active' : 'nav-link')}
            >
              {link.label}
            </NavLink>
          ))}
        </nav>
      </aside>
      <main className="content">
        <header className="topbar">
          <div>
            <strong>{user.fullName}</strong>
            <span className="role-badge">{user.role}</span>
          </div>
          <button type="button" className="secondary" onClick={handleLogout}>
            Log out
          </button>
        </header>
        <section className="page">
          <Outlet />
        </section>
      </main>
    </div>
  );
}
