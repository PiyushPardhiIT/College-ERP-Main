import { useEffect, useState } from 'react';
import { getUsers, updateUserRoles, enableUser, disableUser } from '../../api/adminApi';
import DataTable from '../../components/DataTable';

const ALL_ROLES = ['ADMIN', 'FACULTY', 'STUDENT'];

export default function UserManagement() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState('');

  const loadUsers = async () => {
    try {
      const response = await getUsers(0, 50);
      setUsers(response.data.content); // Spring's Page<> wraps results in "content"
    } catch (err) {
      setError('Failed to load users');
    }
  };

  useEffect(() => {
    loadUsers();
  }, []);

  const handleRoleChange = async (userId, newRole) => {
    try {
      await updateUserRoles(userId, [newRole]);
      loadUsers();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to update role');
    }
  };

  const handleToggleEnabled = async (user) => {
    try {
      if (user.enabled) {
        await disableUser(user.id);
      } else {
        await enableUser(user.id);
      }
      loadUsers();
    } catch (err) {
      setError('Failed to update status');
    }
  };

  const columns = [
    { key: 'id', label: 'ID' },
    { key: 'username', label: 'Username' },
    { key: 'email', label: 'Email' },
    {
      key: 'roles',
      label: 'Role',
      render: (row) => (
        <select
          value={row.roles[0] || ''}
          onChange={(e) => handleRoleChange(row.id, e.target.value)}
        >
          {ALL_ROLES.map((r) => (
            <option key={r} value={r}>{r}</option>
          ))}
        </select>
      ),
    },
    {
      key: 'enabled',
      label: 'Status',
      render: (row) => (row.enabled ? 'Active' : 'Disabled'),
    },
  ];

  return (
    <div>
      <h2>User Management</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <DataTable
        columns={columns}
        rows={users}
        renderActions={(row) => (
          <button onClick={() => handleToggleEnabled(row)}>
            {row.enabled ? 'Disable' : 'Enable'}
          </button>
        )}
      />
    </div>
  );
}