import { useEffect, useState } from 'react';
import { getMyGrades } from '../../api/studentApi';

export default function MyGrades() {
  const [grades, setGrades] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    getMyGrades()
      .then((res) => setGrades(res.data))
      .catch(() => setError('Failed to load grades'));
  }, []);

  return (
    <div>
      <h2>My Grades</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {grades.map((g) => (
        <div key={g.courseId} style={{ border: '1px solid #ccc', padding: '1rem', marginBottom: '1rem' }}>
          <h3>{g.courseName}</h3>
          <p>Total: {g.totalScore} / {g.totalMaxScore} ({g.percentage.toFixed(1)}%)</p>
          <table border="1" cellPadding="6" style={{ borderCollapse: 'collapse' }}>
            <thead>
              <tr><th>Exam Type</th><th>Score</th><th>Max Score</th></tr>
            </thead>
            <tbody>
              {g.details.map((d, i) => (
                <tr key={i}>
                  <td>{d.examType}</td>
                  <td>{d.score}</td>
                  <td>{d.maxScore}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ))}
    </div>
  );
}