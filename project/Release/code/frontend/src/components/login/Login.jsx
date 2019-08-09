import React, { Component } from 'react' ;
import ReactDOM from 'react-dom';
import CodeAnalyser from "../home/CodeAnalyser";

class Login extends Component{
    constructor(props) {
        super(props);
        this.loginFunction = this.loginFunction.bind(this);
    }

    // Function to validate and perform login
    loginFunction(){
        ReactDOM.render(<CodeAnalyser />, document.getElementById('app'));
    }

    render() {
        return (
            <div>
                <section className="login-content">
                    <table>
                        <tr>
                            <td id="loginText">Username</td>
                            <td><input type="text" id="username" ref="uname" required/></td>
                        </tr>
                        <tr>
                            <td id="loginText">Password</td>
                            <td><input type="password" id="password" ref="pass" required/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td className="login-buttons"><button id="loginButton" ref="loginButton" onClick={this.loginFunction}>Login</button></td>
                        </tr>
                    </table>
                </section>
            </div>
        )
    }
}

export default Login ;