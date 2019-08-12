import React, {Component} from 'react';
import Header from '../layouts/Header' ;
import Footer from '../layouts/Footer';
import CCAService from "../services/CCAService";

export default class CodeAnalyser extends Component {
    constructor(props) {
        super(props);
        this.state = {
            filePath: '',
            sourceCode: '',
            totalCTCScore: '',
            catchScore: '',
            switchScore: '',
            controlScore: '',
            iTCScore: '',
            lineNo: 0,
            ctcLineScore: ''
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
                totalCTCScore: response.data.totalCtcCount
            })
        }).catch( (err) => {
            console.log(err);
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
                <Footer/>
            </div>
        )
    }

}
