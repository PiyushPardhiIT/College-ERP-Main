import axiosInstance from './axiosInstance';

// Users
export const getUsers = (page = 0, size = 10) =>
  axiosInstance.get(`/users?page=${page}&size=${size}`);
export const updateUserRoles = (id, roles) =>
  axiosInstance.patch(`/users/${id}/roles`, { roles });
export const enableUser = (id) => axiosInstance.patch(`/users/${id}/enable`);
export const disableUser = (id) => axiosInstance.patch(`/users/${id}/disable`);
export const resetUserPassword = (id, newPassword) =>
  axiosInstance.patch(`/users/${id}/reset-password`, { newPassword });

// Departments
export const getDepartments = () => axiosInstance.get('/departments');
export const createDepartment = (data) => axiosInstance.post('/departments', data);
export const updateDepartment = (id, data) => axiosInstance.put(`/departments/${id}`, data);
export const deleteDepartment = (id) => axiosInstance.delete(`/departments/${id}`);

// Courses
export const getCourses = () => axiosInstance.get('/courses');
export const createCourse = (data) => axiosInstance.post('/courses', data);
export const updateCourse = (id, data) => axiosInstance.put(`/courses/${id}`, data);
export const deleteCourse = (id) => axiosInstance.delete(`/courses/${id}`);