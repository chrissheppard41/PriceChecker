'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import Ajax from 'Ajax';
import Charts from './Charts/Charts';

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {"product": null};
    }

    componentDidMount() {
        let ajax = new Ajax("/price/api/product/", "json", "Product api call");

        ajax.exec().then((data) => {
            this.setState({"product": data});
        });

    }

    render() {
        const { product } = this.state;

        let items = () => <div>Loading</div>;


        if(product !== null) {
            items = product.map((item, index) =>
                <Charts key={item.id} id={item.id} data={item.website} title={item.name}/>
            );
        }



        return (
            <div>
                {items}
            </div>
        )
    }
}


ReactDOM.render(
    <App />,
    document.getElementById('react')
)