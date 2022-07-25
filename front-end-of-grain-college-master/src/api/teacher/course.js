import request from '@/utils/request'

export default {

    /**
     * 添加课程第一步
     * 
     * @param {*} courseInfoForm 
     * @returns 
     */
    addCourseInfo(courseInfoForm) {
        return request({
            url: `/eduservice/edu-course/addCourseInfo`,
            method: 'post',
            data: courseInfoForm
        })
    },

    /**
     * 更新课程
     * 
     * @param {*} courseInfoForm 
     * @returns 
     */
    updateCourseInfo(courseInfoForm) {
        return request({
            url: `/eduservice/edu-course/updateCourseInfo`,
            method: 'post',
            data: courseInfoForm
        })
    },

    /**
     * 根据 ID 查询记录
     * 
     * @param {*} courseId 
     * @returns 
     */
    getCourseById(courseId) {
        return request({
            url: `/eduservice/edu-course/getCourseById/${courseId}`,
            method: 'get'
        })
    },

    /**
     * 根据 ID 查询课程详细记录
     * 
     * @param {*} courseId 
     * @returns 
     */
    getPublishCourseInfo(courseId) {
        return request({
            url: `/eduservice/edu-course/getPublishCourseInfo/${courseId}`,
            method: 'get'
        })
    },

    /**
     * 更新课程
     * 
     * @param {*} courseId 
     * @returns 
     */
    publishCourse(courseId) {
        return request({
            url: `/eduservice/edu-course/publishCourse/${courseId}`,
            method: 'post',
        })
    },

    /**
     * 查询所有课程信息
     * @returns 
     */
    findAllCourse() {
        return request({
            url: `/eduservice/edu-course/findAll`,
            method: 'get',
        })
    },

    /**
     * 多条件分页查询课程信息
     * 
     * @param {*} current 
     * @param {*} limit 
     * @param {*} courseQuery 
     * @returns 
     */
    pageCourseCondition(current, limit, courseQuery) {
        return request({
            url: `/eduservice/edu-course/pageCourseCondition/${current}/${limit}`,
            method: 'post',
            data: courseQuery
        })
    },

    /**
     * 删除所有课程信息
     * 
     * @param {*} courseId 
     * @returns 
     */
    removeCourseById(courseId) {
        return request({
            url: `/eduservice/edu-course/removeCourseByCourseId/${courseId}`,
            method: 'delete'
        })
    },
}