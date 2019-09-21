import APIService from './APIService'

export default class SISService {
    constructor() {
        this.apiService = new APIService();
    }

    //-------------------------------------------------------Staff Functions ----------------------------------------------------------------------------
    getScore(data) {
        return new Promise((resolve, reject) => {
            this.apiService.post('get-score', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }

    getCiDetails(data) {
        return new Promise((resolve, reject) => {
            this.apiService.post('get-ci/by-line', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }

    getFileType(data) {
        return new Promise((resolve, reject) => {
            this.apiService.post('file-type', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error);
            })
        })
    }
}