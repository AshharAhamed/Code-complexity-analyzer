import APIService from './APIService'

export default class SISService {
    constructor() {
        this.apiService = new APIService();
    }

    //-------------------------------------------------------Staff Functions ----------------------------------------------------------------------------
    getScore(data) {
        return new Promise((resolve, reject) => {
            this.apiService.get('get-ctc', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }

    getCode(data) {
        return new Promise((resolve, reject) => {
            this.apiService.get('get-code', data).then(response => {
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }
}