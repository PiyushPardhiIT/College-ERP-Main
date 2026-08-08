import { Outlet, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function DashboardLayout({ links }) {
  const { logout, user } = useAuth();

  return (
    <div>
      <nav style={{ display: 'flex', gap: '1rem', padding: '1rem', borderBottom: '1px solid #ccc' }}>
        <strong>{user?.role} — {user?.username}</strong>
        {links.map((link) => (
          <Link key={link.to} to={link.to}>{link.label}</Link>
        ))}
        <button onClick={logout}>Logout</button>
      </nav>
      <div style={{ padding: '1rem' }}>
        <Outlet />
      </div>
    </div>
  );
}