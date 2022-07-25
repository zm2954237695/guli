import request from '@/utils/request'

export default {

    /**
     * 讲师列表（条件查询分页）
     *
     * @param {Number} current 当前页
     * @param {Number} limit 每页显示记录
     * @param {JSON} teacherQuery 条件对象
     * @returns
     */
    getTeacherListPage(current, limit, teacherQuery) {
        return request({
            // url: '/table/list/' + current + '/' + limit,
            url: `/eduservice/edu-teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            // teacherQuery条件对象，后端使用RequestBody获取数据，data表示把对象转换json进行传递到接口里面
            data: teacherQuery
        })
    },

    /**
     * 根据 id 删除讲师
     *
     * @param {*} id
     * @returns
     */
    removeTeacherById(id) {
        return request({
            url: `/eduservice/edu-teacher/removeTeacherById/${id}`,
            method: 'delete'
        })
    },

    /**
     * 保存讲师
     *
     * @param {*} teacher
     */
    saveTeacher(teacher) {
        return request({
            url: '/eduservice/edu-teacher/addTeacher',
            method: 'post',
            data: teacher
        })
    },

    /**
     * 根据id查询讲师
     * 
     * @param {String}} id 
     * @returns 
     */
    getTeacherById(id) {
        return request({
            url: `/eduservice/edu-teacher/getTeacher/${id}`,
            method: 'get'
        })
    },

    /**
     * 更新讲师
     * 
     * @param {String}} id 
     * @returns 
     */
    updateTeacher(teacher) {
        return request({
            url: `/eduservice/edu-teacher/updateTeacher`,
            method: 'post',
            data: teacher
        })
    },

    /**
     * 讲师列表
     *
     * @returns
     */
    getAllTeacher() {
        return request({
            url: `/eduservice/edu-teacher/findAll`,
            method: 'get',
        })
    },

}