import { useEffect, useState } from 'react';
import { getMyEnrollments, enrollInCourse, getAllCourses } from '../../api/studentApi';
import DataTable from '../../components/DataTable';

export default function MyEnrollments() {
  const [enrollments, setEnrollments] = useState([]);
  const [courses, setCourses] = useState([]);
  const [selectedCourseId, setSelectedCourseId] = useState('');
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const loadData = async () => {
    const [enrollRes, coursesRes] = await Promise.all([getMyEnrollments(), getAllCourses()]);
    setEnrollments(enrollRes.data);
    setCourses(coursesRes.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleEnroll = async (e) => {
    e.preventDefault();
    setError('');
    setMessage('');
    try {
      await enrollInCourse(Number(selectedCourseId));
      setMessage('Enrolled successfully');
      setSelectedCourseId('');
      loadData();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to enroll');
    }
  };

  const enrolledCourseIds = enrollments.map((e) => e.course.id);
  const availableCourses = courses.filter((c) => !enrolledCourseIds.includes(c.id));

  const columns = [
    { key: 'id', label: 'Enrollment ID' },
    { key: 'course', label: 'Course', render: (row) => `${row.course.code} - ${row.course.name}` },
    { key: 'enrolledAt', label: 'Enrolled At', render: (row) => new Date(row.enrolledAt).toLocaleDateString() },
  ];

  return (
    <div>
      <h2>My Enrollments</h2>
      <form onSubmit={handleEnroll} style={{ marginBottom: '1rem' }}>
        <select value={selectedCourseId} onChange={(e) => setSelectedCourseId(e.target.value)} required>
          <option value="">Select a course to enroll</option>
          {availableCourses.map((c) => (
            <option key={c.id} value={c.id}>{c.code} - {c.name}</option>
          ))}
        </select>
        <button type="submit">Enroll</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {message && <p style={{ color: 'green' }}>{message}</p>}
      <DataTable columns={columns} rows={enrollments} />
    </div>
  );
}