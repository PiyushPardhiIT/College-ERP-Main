import { useEffect, useState } from 'react';
import { getAllCourses } from '../../api/facultyApi';
import { Link } from 'react-router-dom';
import DataTable from '../../components/DataTable';

export default function MyCourses() {
  const [courses, setCourses] = useState([]);

  useEffect(() => {
    getAllCourses().then((res) => setCourses(res.data));
  }, []);

  const columns = [
    { key: 'code', label: 'Code' },
    { key: 'name', label: 'Name' },
    { key: 'credits', label: 'Credits' },
  ];

  return (
    <div>
      <h2>Courses</h2>
      <DataTable
        columns={columns}
        rows={courses}
        renderActions={(row) => (
          <Link to={`/faculty/courses/${row.id}`}>Manage</Link>
        )}
      />
    </div>
  );
}