import { useEffect, useState } from 'react';
import { getDepartments, createDepartment, deleteDepartment } from '../../api/adminApi';
import DataTable from '../../components/DataTable';

export default function DepartmentManagement() {
  const [departments, setDepartments] = useState([]);
  const [name, setName] = useState('');
  const [code, setCode] = useState('');
  const [error, setError] = useState('');

  const loadDepartments = async () => {
    const response = await getDepartments();
    setDepartments(response.data);
  };

  useEffect(() => {
    loadDepartments();
  }, []);

  const handleCreate = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await createDepartment({ name, code });
      setName('');
      setCode('');
      loadDepartments();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to create department');
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteDepartment(id);
      loadDepartments();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to delete department');
    }
  };

  const columns = [
    { key: 'id', label: 'ID' },
    { key: 'name', label: 'Name' },
    { key: 'code', label: 'Code' },
  ];

  return (
    <div>
      <h2>Departments</h2>
      <form onSubmit={handleCreate} style={{ marginBottom: '1rem' }}>
        <input placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} required />
        <input placeholder="Code" value={code} onChange={(e) => setCode(e.target.value)} required />
        <button type="submit">Add Department</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <DataTable
        columns={columns}
        rows={departments}
        renderActions={(row) => <button onClick={() => handleDelete(row.id)}>Delete</button>}
      />
    </div>
  );
}