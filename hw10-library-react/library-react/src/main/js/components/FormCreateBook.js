import React from "react";
import * as ReactDOM from "react-dom";

export default class FormCreateBook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            optionAuthor: 0,
            optionGenre: 0
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();

        const newBook = {};
        let self = this;
        this.props.attributes.forEach(attribute => {
            if (attribute === 'author') {
                newBook[attribute] = self.props.authors[self.state.optionAuthor];
            } else if (attribute === 'genre') {
                newBook[attribute] = self.props.genres[self.state.optionGenre];
            } else {
                newBook[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
            }
        });
        this.props.handleCreateBook(newBook);

        // clear out the dialog's inputs
        this.resetInputsForm();

        // Navigate away from the dialog to hide it.
        $('#createBookModal').modal('hide');
    };

    resetInputsForm = (event) => {
        event && event.preventDefault();

        this.props.attributes.forEach(attribute => {
            if (attribute === 'author') {
                this.state.selectedAuthor = 0;
            } else if (attribute === 'genre') {
                this.state.selectedGenre = 0;
            } else {
                ReactDOM.findDOMNode(this.refs[attribute]).value = '';
            }
        });
        //todo
        this.setState({});
    };

    onSelectAuthor = (event) => {
        this.setState({selectedAuthor: event.target.value});
    };

    onSelectGenre = (event) => {
        this.setState({selectedGenre: event.target.value});
    };

    getTagByAttribute = (attribute) => {
        let tag;
        if (attribute === 'author') {
            tag =
                <select defaultValue={this.state.selectedAuthor} className="form-control"
                        onChange={this.onSelectAuthor} required>
                    {this.props.authors.map((author, index) =>
                        <option key={author.id} value={index}>{author.lastName + ' ' + author.firstName}</option>
                    )}
                </select>
        } else if (attribute === 'genre') {
            tag =
                <select defaultValue={this.state.selectedGenre} className="form-control" onChange={this.onSelectGenre}
                        required>
                    {this.props.genres.map((genre, index) =>
                        <option key={genre.id} value={index}>{genre.name}</option>
                    )}
                </select>
        } else {
            tag =
                <input type="text" className="form-control" ref={attribute}
                       placeholder={attribute} required/>
        }
        return tag;
    };

    render() {
        const inputs = this.props.attributes.map(attribute =>
            <div key={attribute} className="form-group">
                <label htmlFor={attribute}>{attribute}</label>
                {this.getTagByAttribute(attribute)}
            </div>
        );

        return (
            <React.Fragment>
                <button type="button" className="btn btn-primary" data-toggle="modal" data-target="#createBookModal">
                    Create book
                </button>

                <div className="modal fade" id="createBookModal" tabIndex="-1" role="dialog"
                     aria-labelledby="Create book" aria-hidden="true" data-keyboard="false" data-backdrop="static">
                    <div className="modal-dialog modal-dialog-centered" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="createBookModalTitle">Create book</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form>
                                <div className="modal-body">
                                    {inputs}
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-dismiss="modal">
                                        Close
                                    </button>
                                    <button onClick={this.handleSubmit} className="btn btn-primary mr-2" type="submit">
                                        Create
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        )
    }

}