import React from "react";
import * as ReactDOM from "react-dom";

export default class FormUpdateBook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modalId: 'updateBookModal-' + props.book.id,
            selectedAuthor: props.authors.findIndex(author => author.id === props.book.author.id),
            selectedGenre: props.genres.findIndex(genre => genre.id === props.book.genre.id)
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();

        let self = this;
        const updatedBook = {};
        this.props.attributes.forEach(attribute => {
            if (attribute === 'author') {
                updatedBook[attribute] = self.props.authors[self.state.selectedAuthor];
            } else if (attribute === 'genre') {
                updatedBook[attribute] = self.props.genres[self.state.selectedGenre];
            } else {
                updatedBook[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
            }
        });
        updatedBook['id'] = this.props.book.id;
        this.props.handleUpdateBook(updatedBook);

        // Navigate away from the dialog to hide it.
        $('#' + this.state.modalId).modal('hide');
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
                <select defaultValue={this.state.selectedAuthor} className="form-control" onChange={this.onSelectAuthor}
                        required>
                    {this.props.authors.map((author, index) =>
                        <option key={this.state.modalId + author.id} value={index}>
                            {author.lastName + ' ' + author.firstName}
                        </option>
                    )}
                </select>
        } else if (attribute === 'genre') {
            tag =
                <select defaultValue={this.state.selectedGenre} className="form-control" onChange={this.onSelectGenre}
                        required>
                    {this.props.genres.map((genre, index) =>
                        <option key={this.state.modalId + genre.id} value={index}>{genre.name}</option>
                    )}
                </select>
        } else {
            tag =
                <input type="text" className="form-control" ref={attribute} disabled={attribute === 'isbn'}
                       defaultValue={this.props.book[attribute]}
                       placeholder={attribute} required/>
        }
        return tag;
    };

    render() {
        const inputs = this.props.attributes.map(attribute =>
            <div key={this.state.modalId + attribute} className="form-group">
                <label htmlFor={attribute}>{attribute}</label>
                {this.getTagByAttribute(attribute)}
            </div>
        );
        return (
            <React.Fragment>
                <button className="btn btn-outline-info btn-sm mr-2" type="button"
                        data-toggle="modal" data-target={'#' + this.state.modalId}>
                    update
                </button>

                <div className="modal fade" id={this.state.modalId} tabIndex="-1" role="dialog"
                     aria-labelledby="Update book" aria-hidden="true" data-keyboard="false" data-backdrop="static">
                    <div className="modal-dialog modal-dialog-centered" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title updateBookModalTitle">Update book</h5>
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form>
                                <div className="modal-body">
                                    {inputs}
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-outline-secondary" data-dismiss="modal">
                                        Close
                                    </button>
                                    <button onClick={this.handleSubmit} className="btn btn-outline-primary mr-2" type="submit">
                                        Update
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