import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Login from './pages/Login';
import AdminLayout from './layouts/AdminLayout';
import UserManagement from './pages/admin/UserManagement';
import DepartmentManagement from './pages/admin/DepartmentManagement';
import CourseManagement from './pages/admin/CourseManagement';
import DashboardLayout from './layouts/DashboardLayout';
import MyEnrollments from './pages/student/MyEnrollments';
import MyAttendance from './pages/student/MyAttendance';
import MyGrades from './pages/student/MyGrades';
import MyCourses from './pages/faculty/MyCourses';
import CourseDetail from './pages/faculty/CourseDetail';

function FacultyDashboardPlaceholder() { return <h1>Faculty Dashboard (Day 12)</h1>; }
function StudentDashboardPlaceholder() { return <h1>Student Dashboard (Day 12)</h1>; }
function Unauthorized() { return <h1>403 - You don't have access to this page</h1>; }

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" replace />} />
          <Route path="/login" element={<Login />} />
          <Route path="/unauthorized" element={<Unauthorized />} />

          <Route
            path="/admin"
            element={
              <ProtectedRoute allowedRoles={['ADMIN']}>
                <AdminLayout />
              </ProtectedRoute>
            }
          >
            <Route index element={<Navigate to="users" replace />} />
            <Route path="users" element={<UserManagement />} />
            <Route path="departments" element={<DepartmentManagement />} />
            <Route path="courses" element={<CourseManagement />} />
          </Route>

          <Route
            path="/faculty"
            element={
              <ProtectedRoute allowedRoles={['FACULTY']}>
                <FacultyDashboardPlaceholder />
              </ProtectedRoute>
            }
          />
<Route
  path="/student"
  element={
    <ProtectedRoute allowedRoles={['STUDENT']}>
      <DashboardLayout links={[
        { to: '/student/enrollments', label: 'Enrollments' },
        { to: '/student/attendance', label: 'Attendance' },
        { to: '/student/grades', label: 'Grades' },
      ]} />
    </ProtectedRoute>
  }
>
  <Route index element={<Navigate to="enrollments" replace />} />
  <Route path="enrollments" element={<MyEnrollments />} />
  <Route path="attendance" element={<MyAttendance />} />
  <Route path="grades" element={<MyGrades />} />
</Route>

<Route
  path="/faculty"
  element={
    <ProtectedRoute allowedRoles={['FACULTY']}>
      <DashboardLayout links={[{ to: '/faculty/courses', label: 'Courses' }]} />
    </ProtectedRoute>
  }
>
  <Route index element={<Navigate to="courses" replace />} />
  <Route path="courses" element={<MyCourses />} />
  <Route path="courses/:courseId" element={<CourseDetail />} />
</Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;