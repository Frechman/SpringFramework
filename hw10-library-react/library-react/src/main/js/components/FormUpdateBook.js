import React from "react";
import * as ReactDOM from "react-dom";

const root = '/api/v1';

export default class FormUpdateBook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modalId: 'updateBookModal-' + props.book.id,
            genres: [],
            authors: [],
            optionAuthor: 0,
            optionGenre: 0
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();

        const updatedBook = {};
        this.props.attributes.forEach(attribute => {
            if (attribute === 'author') {
                updatedBook[attribute] = this.state.authors[this.state.optionAuthor];
            } else if (attribute === 'genre') {
                updatedBook[attribute] = this.state.genres[this.state.optionGenre];
            } else {
                updatedBook[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
            }
        });
        updatedBook['id'] = this.props.book.id;
        this.props.handleUpdateBook(updatedBook);

        // Navigate away from the dialog to hide it.
        $(this.state.modalId).modal('hide');
    };

    onSelectAuthor = (event) => {
        this.setState({optionAuthor: event.target.value});
    };

    onSelectGenre = (event) => {
        this.setState({optionGenre: event.target.value});
    };

    getTagByAttribute = (attribute) => {
        let tag;
        if (attribute === 'author') {
            tag =
                <select value={this.state.optionAuthor} className="form-control" onChange={this.onSelectAuthor}
                        required>
                    {this.state.authors.map((author, index) =>
                        <option key={this.state.modalId + author.id} value={index}>
                            {author.lastName + ' ' + author.firstName}
                        </option>
                    )}
                </select>
        } else if (attribute === 'genre') {
            tag =
                <select value={this.state.optionGenre} className="form-control" onChange={this.onSelectGenre} required>
                    {this.state.genres.map((genre, index) =>
                        <option key={this.state.modalId + genre.id} value={index}>{genre.name}</option>
                    )}
                </select>
        } else {
            tag =
                <input type="text" className="form-control" ref={attribute}
                       defaultValue={this.props.book[attribute]}
                       placeholder={attribute} required/>
        }
        return tag;
    };

    loadAuthorsAndGenresFromServer = () => {
        Promise.all([
            fetch(root + '/authors'),
            fetch(root + '/genres')
        ])
            .then(([res1, res2]) => Promise.all([res1.json(), res2.json()]))
            .then(([authors, genres]) => this.setState({authors, genres}));
    };

    componentDidMount() {
        this.loadAuthorsAndGenresFromServer();
        this.setState({
            optionAuthor: this.state.authors.indexOf(this.props.book.author),
            optionGenre: this.state.genres.indexOf(this.props.book.genre)
        })
    }

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
                                    <button type="button" className="btn btn-secondary" data-dismiss="modal">
                                        Close
                                    </button>
                                    <button onClick={this.handleSubmit} className="btn btn-primary mr-2" type="submit">
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