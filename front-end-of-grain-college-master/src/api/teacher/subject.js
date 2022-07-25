import request from '@/utils/request'

export default {
    /**
     * 获取所有课程分类
     * 
     * @param {*} current 
     * @param {*} limit 
     * @param {*} teacherQuery 
     * @returns 
     */
    getAllSubject() {
        return request({
            url: `/eduservice/edu-subject/getAllSubject`,
            method: 'get'
        })
    },
}