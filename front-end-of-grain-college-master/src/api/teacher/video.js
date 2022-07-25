import request from '@/utils/request'

export default {

    /**
     * 增加小节
     * 
     * @param {*} video 
     * @returns 
     */
    addVideo(video) {
        return request({
            url: `/eduservice/edu-video/addVideo`,
            method: 'post',
            data: video
        })
    },

    /**
     * 查询小节
     * 
     * @param {*}} videoId 
     * @returns 
     */
    getVideoInfo(videoId) {
        return request({
            url: `/eduservice/edu-video/getVideoInfo/${videoId}`,
            method: 'get'
        })
    },

    /**
     * 增加小节
     * 
     * @param {*} video 
     * @returns 
     */
    updateVideo(video) {
        return request({
            url: `/eduservice/edu-video/updateVideo`,
            method: 'post',
            data: video
        })
    },

    /**
     * 删除小节
     * 
     * @param {*} videoId 
     */
    removeVideo(videoId) {
        return request({
            url: `/eduservice/edu-video/removeVideo/${videoId}`,
            method: 'delete'
        })
    },

    /**
     * 删除阿里云视频
     * 
     * @param {*} videoId 
     */
     removeAliyunVideo(videoId) {
        return request({
            url: `/eduvod/video/deleteAliyunVideo/${videoId}`,
            method: 'delete'
        })
    }


}