import request from '@/utils/request'

export default {

    /**
     * 获取所有章节小节
     * 
     * @param {*} courseInfoForm 
     * @returns 
     */
    getAllChapterVideo(courseId) {
        return request({
            url: `/eduservice/edu-chapter/getChapterVideo/${courseId}`,
            method: 'get'
        })
    },

    /**
     * 增加章节
     * 
     * @param {*} chapter 
     * @returns 
     */
    addChapter(chapter) {
        return request({
            url: `/eduservice/edu-chapter/addChapter`,
            method: 'post',
            data: chapter
        })
    },

    /**
     * 查询章节
     * 
     * @param {*}} chapterId 
     * @returns 
     */
    getChapterInfo(chapterId) {
        return request({
            url: `/eduservice/edu-chapter/getChapterInfo/${chapterId}`,
            method: 'get'
        })
    },

    /**
     * 增加章节
     * 
     * @param {*} chapter 
     * @returns 
     */
    updateChapter(chapter) {
        return request({
            url: `/eduservice/edu-chapter/updateChapter`,
            method: 'post',
            data: chapter
        })
    },

    /**
     * 删除章节
     * 
     * @param {*} chapterId 
     */
    removeChapter(chapterId) {
        return request({
            url: `/eduservice/edu-chapter/removeChapter/${chapterId}`,
            method: 'delete'
        })
    }


}