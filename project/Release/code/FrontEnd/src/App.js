import React, {Component} from 'react';
import {BrowserRouter as Router, Route} from "react-router-dom";
import Login from './components/login/Login'
import Home from './components/home/CodeAnalyser'

class App extends Component {
    render() {
        return (
            <Router>
                <div>
                    <Route exact path="/" component={Home}/>
                </div>
            </Router>
        );
    }
}

export default App;
