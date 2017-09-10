import React from 'react';
import { VictoryChart, VictoryLine } from 'victory';

class Charts extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        let items = this.props.data.map((item, index) =>
            <VictoryLine
                key={item.id}
                style={{
                    data: { stroke: item.provider.colour },
                    parent: { border: "1px solid #ccc"}
                }}
                data={item.priceList}
            />
        );
        let key = this.props.data.map((item, index) => {
                let style = {
                    color: item.provider.colour
                };
                return <span key={item.id} style={style}>{item.provider.name}<br /></span>
            }
        );
        let width = {
            margin: "0 auto",
            width: "60%"
        };


        return (
            <div className="charts">
                <h2>{this.props.title}</h2>
                <div className="key">
                    <h4>Key</h4>
                    {key}
                </div>
                <section style={width}>
                    <VictoryChart>
                        {items}
                    </VictoryChart>
                </section>
            </div>
        )
    }
}

export default Charts;