fetch('/api/videos')
    .then(response => response.json())
    .then(data => {
        const margin = {top: 20, right: 30, bottom: 50, left: 50};
        const width = document.getElementById('chart').clientWidth - margin.left - margin.right;
        const height = 400 - margin.top - margin.bottom;



        const svg = d3.select("#chart")
            .append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", `translate(${margin.left},${margin.top})`);

        const x = d3.scaleBand()
            .domain(data.map(d => d.title))
            .range([0, width])
            .padding(0.1);

        const y = d3.scaleLinear()
            .domain([0, d3.max(data, d => d.views)])
            .nice()
            .range([height, 0]);

        svg.append("g")
            .selectAll(".bar")
            .data(data)
            .enter()
            .append("rect")
            .attr("class", "bar")
            .attr("x", d => x(d.title))
            .attr("y", d => y(d.views))
            .attr("width", x.bandwidth())
            .attr("height", d => height - y(d.views));

        svg.append("g")
            .attr("class", "x-axis")
            .attr("transform", `translate(0,${height})`)
            .call(d3.axisBottom(x))
            .selectAll("text")
            .attr("class", "axis-label")
            .attr("transform", "rotate(-45)")
            .attr("fill","steelblue")
            .style("text-anchor", "end");

        svg.append("g")
            .attr("class", "y-axis")
            .call(d3.axisLeft(y))
            .append("text")
            .attr("class", "axis-label")
            .attr("y", -10)
            .attr("x", -height / 2)
            .attr("transform", "rotate(-90)")
            .style("text-anchor", "middle")
            .text("Views");
    });