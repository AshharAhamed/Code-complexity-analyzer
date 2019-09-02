import React, {Component} from 'react';
import Header from '../layouts/Header' ;
import Footer from '../layouts/Footer';
import CCAService from "../services/CCAService";
import Modal from "react-awesome-modal";
import 'bootstrap/dist/css/bootstrap.css';
import logo from '../../resources/images/neo_logo.png';

export default class CodeAnalyser extends Component {
    constructor(props) {
        super(props);
        this.state = {
            filePath: '',
            sourceCode: '',
            totalCTCScore: '',
            ctcLineScore: '',
            errorList: '',
            showErrorFlag: false,
            errorMessage: '',

            ciDetails: [],
            ciValues: []
        };
        this.CCAService = new CCAService();
        this.getScore = this.getScore.bind(this);
        this.onChange = this.onChange.bind(this);
        this.getCiScore = this.getCiScore.bind(this);
        this.returnCiValue = this.returnCiValue.bind(this);
    }


    onChange(e) {
        this.setState({
            [e.target.name]: e.target.value,
            edit: true,
        });
    }

    getScore() {
        this.CCAService.getScore(this.state.filePath).then(response => {
            this.setState({
                ctcLineScore: response.data.lineScore,
                sourceCode: response.data.code,
                totalCTCScore: response.data.totalCtcCount,
                errorList: response.data.errorList,
                errorMessage: response.data.errorMessage
            });

            if (this.state.errorList != null) {
                this.setState({showErrorFlag: true})
            } else
                this.setState({showErrorFlag: false})

            if (this.state.errorMessage != null) {
                alert(this.state.errorMessage)
            }
        }).catch((err) => {
            console.log(err);
        });

        this.getCiScore();
    }

    getCiScore() {
        this.CCAService.getCiDetails(this.state.filePath).then(res => {
            let data = res.data;
            this.setState({
                ciDetails: data
            })
        });
    }


    closeModal() {
        this.setState({
            showErrorFlag: false
        });
    }

    returnCiValue(index) {
        if (typeof this.state.ciDetails[index + 1] !== 'undefined') {
            if (this.state.ciDetails.hasOwnProperty(index + 1)) {
                if (this.state.ctcLineScore[index] > 0) {
                    return this.state.ciDetails[index + 1].totalCiValue + 2
                } else if (this.state.ctcLineScore[index] == 0) {
                    return this.state.ciDetails[index + 1].totalCiValue;
                }
            }
        } else if (this.state.ctcLineScore[index] > 0) {
            return 2;
        } else {
            return 0;
        }

    }


    // The Essential render function
    render() {
        let errorTable = () => {
            if (this.state.errorList) {
                return <table>
                    <tbody>

                    <tr>
                        {this.state.errorList && this.state.errorList.map((line, index) => {
                            return (

                                <td style={{paddingRight: 100}}>
                                    <pre style={{fontSize: 15}}>{line}</pre>
                                </td>

                            )
                        })}
                    </tr>

                    </tbody>
                </table>
            }
        };
        let analyzedResult = () => {
            if (this.state.sourceCode) {
                return <table border="1" style={{marginLeft: "auto", marginRight: "auto", width: "90%"}}
                              className="table table-striped">
                    <thead>
                    <tr>
                        <th rowSpan="2">Line No</th>
                        <th rowSpan="2">Code</th>
                        <th rowSpan="2">Ctc</th>
                        <th rowSpan="2">Class Mapping</th>
                        <th rowSpan="2">Ci</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.sourceCode && this.state.sourceCode.map((line, index) => {
                        return (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td style={{paddingRight: 100}}>
                                    <pre style={{fontSize: 15}}>{line}</pre>
                                </td>
                                <td style={{paddingRight: 100}}>
                                    <pre style={{fontSize: 15}}>{this.state.ctcLineScore[index]}</pre>
                                </td>

                                <td>
                                    {(this.state.ciDetails.hasOwnProperty(index + 1)) ? this.state.ciDetails[index + 1].classHierachy : ''}
                                </td>
                                <td>

                                    {/*{(((this.state.ciDetails.hasOwnProperty(index + 1)) && this.state.ctcLineScore[index] > 0) ? (2 + this.state.ciDetails[index + 1].totalCiValue) : 0) || (this.state.ctcLineScore[index] > 0 ? 2 : 0) || ((this.state.ciDetails.hasOwnProperty(index + 1)) && this.state.ctcLineScore[index] == 0) ? (this.state.ciDetails[index + 1].totalCiValue) : 0}*/}
                                    {this.returnCiValue(index)}
                                </td>
                            </tr>
                        )
                    })}
                    </tbody>
                </table>
            }
        };
        return (
            <div>
                <Header/>

                <div id="ccaMainBody" style={{margin: 0}}>
                    <section id="mainBodySection" style={{margin: "2%"}}>

                        <h3>Generate Complexity Matrices</h3>
                        <div style={{width: '100%'}} className="form-group">
                            <input name="filePath" type="text" placeholder="File Path"
                                   onChange={this.onChange}/>
                        </div>
                        <button className="basicButton" ref="calcButton"
                                onClick={this.getScore}>Calculate
                        </button>
                        <section id="complexityTable">
                            <p>* Note - Comments , text inside double quotes will be removed</p>
                        </section>
                        <br/>
                        <br/>
                    </section>
                    <section id="complexityTable">
                    </section>

                    {analyzedResult()}


                </div>

                {/*Here comes the pop up windows*/}
                <Modal visible={this.state.showErrorFlag} width="2000" height="400" effect="fadeInRight"
                       onClickAway={() => this.closeModal()}>

                    <div className="container p-2" style={{marginBottom: '500px', paddingBottom: '500px'}}>
                        <div className="wrap-input100 validate-input" data-validate="Name is required">
                            <span className="label-input100"
                                  style={{color: 'red', marginLeft: 980, size: 100}}>Error</span>
                            <div style={{height: 100}}>
                                <div className={"body"}>
                                    {errorTable()}
                                </div>
                                <div className="col-lg mt-3">
                                    <input type="button" className="btn btn-info btn-block" value="Close"
                                           onClick={() => this.closeModal()}/>
                                </div>
                            </div>
                        </div>
                    </div>
                </Modal>

                <Footer/>
            </div>
        )
    }

}
