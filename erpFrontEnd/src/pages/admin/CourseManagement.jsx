import { useEffect, useState } from 'react';
import { getCourses, createCourse, deleteCourse, getDepartments } from '../../api/adminApi';
import DataTable from '../../components/DataTable';

export default function CourseManagement() {
  const [courses, setCourses] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [code, setCode] = useState('');
  const [name, setName] = useState('');
  const [credits, setCredits] = useState(3);
  const [departmentId, setDepartmentId] = useState('');
  const [error, setError] = useState('');

  const loadData = async () => {
    const [coursesRes, deptRes] = await Promise.all([getCourses(), getDepartments()]);
    setCourses(coursesRes.data);
    setDepartments(deptRes.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleCreate = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await createCourse({
        code,
        name,
        credits: Number(credits),
        department: { id: Number(departmentId) },
      });
      setCode('');
      setName('');
      setCredits(3);
      setDepartmentId('');
      loadData();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to create course');
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteCourse(id);
      loadData();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to delete course');
    }
  };

  const columns = [
    { key: 'id', label: 'ID' },
    { key: 'code', label: 'Code' },
    { key: 'name', label: 'Name' },
    { key: 'credits', label: 'Credits' },
    { key: 'department', label: 'Department', render: (row) => row.department?.name || '-' },
  ];

  return (
    <div>
      <h2>Courses</h2>
      <form onSubmit={handleCreate} style={{ marginBottom: '1rem' }}>
        <input placeholder="Code" value={code} onChange={(e) => setCode(e.target.value)} required />
        <input placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} required />
        <input type="number" placeholder="Credits" value={credits} onChange={(e) => setCredits(e.target.value)} required />
        <select value={departmentId} onChange={(e) => setDepartmentId(e.target.value)} required>
          <option value="">Select Department</option>
          {departments.map((d) => (
            <option key={d.id} value={d.id}>{d.name}</option>
          ))}
        </select>
        <button type="submit">Add Course</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <DataTable
        columns={columns}
        rows={courses}
        renderActions={(row) => <button onClick={() => handleDelete(row.id)}>Delete</button>}
      />
    </div>
  );
}