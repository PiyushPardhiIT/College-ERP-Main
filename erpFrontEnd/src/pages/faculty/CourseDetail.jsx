import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getAllEnrollments, markAttendance, recordMarks, getAttendanceForCourse, getMarksForCourse } from '../../api/facultyApi';
import DataTable from '../../components/DataTable';

export default function CourseDetail() {
  const { courseId } = useParams();
  const [enrollments, setEnrollments] = useState([]);
  const [attendance, setAttendance] = useState([]);
  const [marks, setMarks] = useState([]);
  const [attendanceDate, setAttendanceDate] = useState('');
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const loadData = async () => {
    const [enrollRes, attendanceRes, marksRes] = await Promise.all([
      getAllEnrollments(),
      getAttendanceForCourse(courseId),
      getMarksForCourse(courseId),
    ]);
    setEnrollments(enrollRes.data.filter((e) => e.course.id === Number(courseId)));
    setAttendance(attendanceRes.data);
    setMarks(marksRes.data);
  };

  useEffect(() => {
    loadData();
  }, [courseId]);

  const handleMarkAttendance = async (enrollmentId, status) => {
    setError('');
    setMessage('');
    if (!attendanceDate) {
      setError('Pick a date first');
      return;
    }
    try {
      await markAttendance(enrollmentId, attendanceDate, status);
      setMessage('Attendance recorded');
      loadData();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to mark attendance');
    }
  };

  const handleRecordMarks = async (enrollmentId, examType, score, maxScore) => {
    setError('');
    setMessage('');
    try {
      await recordMarks(enrollmentId, examType, Number(score), Number(maxScore));
      setMessage('Marks recorded');
      loadData();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to record marks');
    }
  };

  return (
    <div>
      <h2>Course Roster</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {message && <p style={{ color: 'green' }}>{message}</p>}

      <div style={{ marginBottom: '1rem' }}>
        <label>Attendance date: </label>
        <input type="date" value={attendanceDate} onChange={(e) => setAttendanceDate(e.target.value)} />
      </div>

      <table border="1" cellPadding="8" style={{ borderCollapse: 'collapse', width: '100%' }}>
        <thead>
          <tr>
            <th>Enrollment ID</th>
            <th>Student</th>
            <th>Mark Attendance</th>
            <th>Record Marks</th>
          </tr>
        </thead>
        <tbody>
          {enrollments.map((enrollment) => (
            <StudentRow
              key={enrollment.id}
              enrollment={enrollment}
              onMarkAttendance={handleMarkAttendance}
              onRecordMarks={handleRecordMarks}
            />
          ))}
        </tbody>
      </table>

      <h3 style={{ marginTop: '2rem' }}>Attendance Log</h3>
      <DataTable
        columns={[
          { key: 'date', label: 'Date' },
          { key: 'status', label: 'Status' },
          { key: 'enrollment', label: 'Enrollment ID', render: (row) => row.enrollment.id },
        ]}
        rows={attendance}
      />

      <h3 style={{ marginTop: '2rem' }}>Marks Log</h3>
      <DataTable
        columns={[
          { key: 'examType', label: 'Exam Type' },
          { key: 'score', label: 'Score' },
          { key: 'maxScore', label: 'Max Score' },
          { key: 'enrollment', label: 'Enrollment ID', render: (row) => row.enrollment.id },
        ]}
        rows={marks}
      />
    </div>
  );
}

function StudentRow({ enrollment, onMarkAttendance, onRecordMarks }) {
  const [examType, setExamType] = useState('MIDTERM');
  const [score, setScore] = useState('');
  const [maxScore, setMaxScore] = useState('');

  return (
    <tr>
      <td>{enrollment.id}</td>
      <td>{enrollment.studentProfile?.rollNumber || enrollment.studentProfile?.user?.username}</td>
      <td>
        <button onClick={() => onMarkAttendance(enrollment.id, 'PRESENT')}>Present</button>
        <button onClick={() => onMarkAttendance(enrollment.id, 'ABSENT')}>Absent</button>
      </td>
      <td>
        <select value={examType} onChange={(e) => setExamType(e.target.value)}>
          <option value="MIDTERM">Midterm</option>
          <option value="FINAL">Final</option>
          <option value="ASSIGNMENT">Assignment</option>
          <option value="QUIZ">Quiz</option>
        </select>
        <input placeholder="Score" value={score} onChange={(e) => setScore(e.target.value)} style={{ width: '60px' }} />
        <input placeholder="Max" value={maxScore} onChange={(e) => setMaxScore(e.target.value)} style={{ width: '60px' }} />
        <button onClick={() => onRecordMarks(enrollment.id, examType, score, maxScore)}>Save</button>
      </td>
    </tr>
  );
}