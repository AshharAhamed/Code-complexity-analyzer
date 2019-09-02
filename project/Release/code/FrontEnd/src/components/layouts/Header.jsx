import React, {Component} from 'react' ;
import logo from "../../resources/images/neo_logo.png";

class Header extends Component {
    render() {
        return (
            <div>
                <header>
                    <div>
                        <img src={logo} style={{width: "10%", height: "10%"}}/>
                    </div>
                    <h2>Code Complexity Analyzer</h2>
                    <h6>Developed by Team Lead Neo, a team of developers who never gives up</h6>
                </header>
            </div>
        )
    }
}

export default Header;