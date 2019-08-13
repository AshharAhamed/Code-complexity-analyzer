import React, {Component} from 'react';
import Header from '../layouts/Header' ;
import Footer from '../layouts/Footer';
import CCAService from "../services/CCAService";
import Modal from "react-awesome-modal";

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
            errorMessage: ''
        };
        this.CCAService = new CCAService();
        this.getScore = this.getScore.bind(this);
        this.onChange = this.onChange.bind(this);
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

            if(this.state.errorMessage != null){
                alert(this.state.errorMessage)
            }
        }).catch((err) => {
            console.log(err);
        });
    }

    closeModal() {
        this.setState({
            showErrorFlag: false
        });
    }


    // The Essential render function
    render() {
        return (
            <div>
                <Header/>
                <div id="ccaMainBody">
                    <section id="mainBodySection">
                        <h3>Generate Complexity Matrices</h3>
                        <div style={{width: 1500}}>
                            <input name="filePath" type="text" className="btn btn-info" placeholder="File Path"
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

                    <table border="1">
                        <thead>
                        <th rowSpan="2">Line No</th>
                        <th rowSpan="2">Code</th>
                        <th rowSpan="2">Ctc</th>
                        </thead>
                        <tbody>
                        {this.state.sourceCode && this.state.sourceCode.map((line, index) => {
                            return (
                                <tr>
                                    <td>{index + 1}</td>
                                    <td style={{paddingRight: 100}}>
                                        <pre style={{fontSize: 15}}>{line}</pre>
                                    </td>
                                    <td style={{paddingRight: 100}}>
                                        <pre style={{fontSize: 15}}>{this.state.ctcLineScore[index]}</pre>
                                    </td>
                                </tr>
                            )
                        })}
                        </tbody>
                    </table>
                </div>

                {/*Here comes the pop up windows*/}
                <Modal visible={this.state.showErrorFlag} width="2000" height="400" effect="fadeInRight"
                       onClickAway={() => this.closeModal()}>

                    <div className="container p-2" style={{marginBottom: '500px', paddingBottom: '500px'}}>
                        <div className="wrap-input100 validate-input" data-validate="Name is required">
                            <span className="label-input100" style={{color:'red', marginLeft:980, size:100}}>Error</span>
                            <div style={{height: 100}}>
                                <div className={"body"}>
                                    <table>
                                        <tr>

                                            {this.state.errorList && this.state.errorList.map((line, index) => {
                                                return (
                                                    <tr>
                                                        <td style={{paddingRight: 100}}>
                                                            <pre style={{fontSize: 15}}>{line}</pre>
                                                        </td>
                                                    </tr>
                                                )
                                            })}
                                        </tr>
                                    </table>
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
