import axios from 'axios'

export default class APIService {

    constructor() {
        this.baseUrl = "http://localhost:8080/";
    }

    get(url, path) {
        return new Promise((resolve, reject) => {
            this.messageHeaders = {
                "file-path": path
            };
            axios.get(this.baseUrl + url, {headers: this.messageHeaders}).then(response => {
                if (response.data.message === "Error") {
                    alert("Error Please Login Again !");
                }
                resolve(response);
            }).catch(err => {
                resolve(err);
            });
        });
    }
}