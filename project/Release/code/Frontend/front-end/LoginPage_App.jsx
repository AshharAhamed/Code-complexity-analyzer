import React, {Component} from 'react';
import Header from './Common_Components/Header' ;
import Footer from './Common_Components/Footer';
import Login from './Login_Components/Login';

export default class LoginPage_App extends Component {
    constructor(props) {
        super(props);
    }

    // The Essential render function
    // This constructs the login page with a header, body and a footer
    render() {
        return (
            <div>
                <Header/>
                <section id="mainLoginBodySection">
                    <Login/>
                </section>
                <Footer/>
            </div>
        )
    }
}
