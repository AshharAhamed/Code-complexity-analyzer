import APIService from './APIService'

export default class SISService {
    constructor() {
        this.apiService = new APIService();
    }

    //-------------------------------------------------------Staff Functions ----------------------------------------------------------------------------
    getScore(data) {
        return new Promise((resolve, reject) => {
            this.apiService.get('get-score', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }

    getCiDetails(data) {
        return new Promise((resolve, reject) => {
            this.apiService.get('get-ci/by-line', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }

    getCrJavaDetails(data){
        return new Promise((resolve, reject) => {
            this.apiService.get('get-cr/recursive-java', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }
    getCsValues(data){
        return new Promise((resolve, reject) => {
            this.apiService.get('get-cs', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }

    getCrCppDetails(data){
        return new Promise((resolve, reject) => {
            this.apiService.get('get-cr/recursive-cpp', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }

    getFileType(data) {
        return new Promise((resolve, reject) => {
            this.apiService.get('file-type', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error);
            })
        })
    }
}