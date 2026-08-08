import { Outlet, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function AdminLayout() {
  const { logout, user } = useAuth();

  return (
    <div>
      <nav style={{ display: 'flex', gap: '1rem', padding: '1rem', borderBottom: '1px solid #ccc' }}>
        <strong>Admin — {user?.username}</strong>
        <Link to="/admin/users">Users</Link>
        <Link to="/admin/departments">Departments</Link>
        <Link to="/admin/courses">Courses</Link>
        <button onClick={logout}>Logout</button>
      </nav>
      <div style={{ padding: '1rem' }}>
        <Outlet />
      </div>
    </div>
  );
}