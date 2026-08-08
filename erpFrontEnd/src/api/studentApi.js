import axiosInstance from './axiosInstance';

export const getMyEnrollments = () => axiosInstance.get('/enrollments/me');
export const enrollInCourse = (courseId) => axiosInstance.post('/enrollments', { courseId });
export const getAllCourses = () => axiosInstance.get('/courses');
export const getMyAttendance = () => axiosInstance.get('/attendance/me');
export const getMyGrades = () => axiosInstance.get('/marks/me');