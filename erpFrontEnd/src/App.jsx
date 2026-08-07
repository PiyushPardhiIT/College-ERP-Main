import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Login from './pages/Login';

function AdminDashboardPlaceholder() { return <h1>Admin Dashboard (Day 11)</h1>; }
function FacultyDashboardPlaceholder() { return <h1>Faculty Dashboard (Day 12)</h1>; }
function StudentDashboardPlaceholder() { return <h1>Student Dashboard (Day 12)</h1>; }
function Unauthorized() { return <h1>403 - You don't have access to this page</h1>; }

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/unauthorized" element={<Unauthorized />} />
          <Route
            path="/admin"
            element={
              <ProtectedRoute allowedRoles={['ADMIN']}>
                <AdminDashboardPlaceholder />
              </ProtectedRoute>
            }
          />
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
                <StudentDashboardPlaceholder />
              </ProtectedRoute>
            }
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;