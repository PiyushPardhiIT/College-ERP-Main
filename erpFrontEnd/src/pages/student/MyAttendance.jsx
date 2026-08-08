import { useEffect, useState } from 'react';
import { getMyAttendance } from '../../api/studentApi';
import DataTable from '../../components/DataTable';

export default function MyAttendance() {
  const [attendance, setAttendance] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    getMyAttendance()
      .then((res) => setAttendance(res.data))
      .catch(() => setError('Failed to load attendance'));
  }, []);

  const columns = [
    { key: 'date', label: 'Date' },
    { key: 'status', label: 'Status' },
    { key: 'enrollment', label: 'Course', render: (row) => row.enrollment?.course?.name || '-' },
  ];

  return (
    <div>
      <h2>My Attendance</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <DataTable columns={columns} rows={attendance} />
    </div>
  );
}