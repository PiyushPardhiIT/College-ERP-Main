import axiosInstance from './axiosInstance';

export const getAllCourses = () => axiosInstance.get('/courses');
export const getAllEnrollments = () => axiosInstance.get('/enrollments');
export const markAttendance = (enrollmentId, date, status) =>
  axiosInstance.post('/attendance', { enrollmentId, date, status });
export const getAttendanceForCourse = (courseId) =>
  axiosInstance.get(`/attendance/course/${courseId}`);
export const recordMarks = (enrollmentId, examType, score, maxScore) =>
  axiosInstance.post('/marks', { enrollmentId, examType, score, maxScore });
export const getMarksForCourse = (courseId) =>
  axiosInstance.get(`/marks/course/${courseId}`);