import React, {Component} from 'react';
import Header from '../Common_Components/Header' ;
import Footer from '../Common_Components/Footer';
import ReactDOM from "react-dom";

export default class CodeAnalyser extends Component {
    constructor(props) {
        super(props);
        this.generateComplexityTable = this.generateComplexityTable.bind(this);
    }

    // Function to generate Initial table
    generateComplexityTable(){
        let tableHolder = document.getElementById('complexityTable');
        let messageToDevelopers = "You get a type error, probably because of a CORS misconfiguration. \n" +
            "Please look at the fetch method inside the \'CodeAnalyser.jsx\'. \n" +
            "That resource url works fine!. \n" +
            "Lets find a solution to this next week!\n" ;
        tableHolder.innerHTML = messageToDevelopers ; // Clearing out the element

        let fetchInput = 'http://localhost:8080/get-code';
        fetch(fetchInput, {
            method: 'GET',
            headers: {'Content-Type': 'application/json'},
        }).then(response => {
            return response.json();
        }).then(data => {
            console.log(data);
            // tableHolder.innerHTML = data ;
        }).catch(err => {
            alert(err);
        })
    }

    // The Essential render function
    render() {
        return (
            <div>
                <Header/>
                <div id="ccaMainBody">
                    <section id="mainBodySection">
                        <h3>Generate Complexity Matrices</h3>
                        <button className="basicButton" ref="calcButton" onClick={this.generateComplexityTable}>Calculate</button>
                    </section>
                    <section id="complexityTable">
                        <p>The code analysis should be plugged into this section element</p>
                    </section>
                </div>
                <Footer/>
            </div>
        )
    }

}
