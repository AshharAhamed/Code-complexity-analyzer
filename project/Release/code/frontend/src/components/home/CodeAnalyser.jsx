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
            iTCScore: ''
        };
        this.generateComplexityTable = this.generateComplexityTable.bind(this);
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
                sourceCode: response.data.SourceCode,
                catchScore: response.data.CatchScore,
                switchScore: response.data.SwitchScore,
                controlScore: response.data.ControlScore,
                iTCScore: response.data.ITCScore,
                totalCTCScore: response.data.TotalCTCScore
            })
        }).catch(function (error) {
            console.log(error);
        });
    }

    // Function to generate Initial table
    generateComplexityTable() {
        alert(this.state.FileName);
        // let tableHolder = document.getElementById('complexityTable');
        // let messageToDevelopers = "You get a type error, probably because of a CORS misconfiguration. \n" +
        //     "Please look at the fetch method inside the \'CodeAnalyser.jsx\'. \n" +
        //     "That resource url works fine!. \n" +
        //     "Lets find a solution to this next week!\n";
        // tableHolder.innerHTML = messageToDevelopers; // Clearing out the element
        //
        // let fetchInput = 'http://localhost:8080/get-code';
        // fetch(fetchInput, {
        //     method: 'GET',
        //     headers: {'Content-Type': 'application/json'},
        // }).then(response => {
        //     return response.json();
        // }).then(data => {
        //     console.log(data);
        //     // tableHolder.innerHTML = data ;
        // }).catch(err => {
        //     alert(err);
        // })
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
                    </section>
                    <section id="complexityTable">
                        <p>The code analysis should be plugged into this section element</p>
                    </section>
                </div>

                <table border="1">
                    <thead>
                    <tr>
                        <th>Code</th>
                        <tr>
                            <th>Score</th>
                        </tr>
                        <tr>
                        <th>Catch</th>
                        <th>Switch</th>
                        <th>Control</th>
                        <th>ITC</th>
                        <th>Total CTC</th>
                        </tr>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <pre>{this.state.sourceCode}</pre>
                        </td>
                        <td>{this.state.catchScore}</td>
                        <td>{this.state.switchScore}</td>
                        <td>{this.state.controlScore}</td>
                        <td>{this.state.iTCScore}</td>
                        <td>{this.state.totalCTCScore}</td>
                    </tr>

                    </tbody>

                </table>

                <Footer/>
            </div>
        )
    }

}
