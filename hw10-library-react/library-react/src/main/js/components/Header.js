import React, {Fragment} from "react";

export default class Header extends React.Component {

    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (
            <Fragment>
                <div className="row">
                    <div className="col-sm-4"></div>{/*frontend-80lvl*/}
                    <div className="col-sm-4">
                        <h3 className="text-center">{this.props.title}</h3>
                    </div>
                    <div className="text-right pr-5 col-sm-4">
                        <form action="/logout" method="post">
                            <input type="submit" value="Sign Out" className="btn btn-outline-secondary"/>
                        </form>
                    </div>
                </div>
            </Fragment>
        )
    }
}