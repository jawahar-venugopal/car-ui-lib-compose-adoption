import plotly.graph_objects as go
from plotly.subplots import make_subplots

percentiles = ["P50", "P90", "P95", "P99"]

frameDurationCpuMs = {
    "CarUiListItemActivity": {
        "XML":     [26.8, 31.9, 33.7, 44.2],
        "Compose": [29.0, 44.6, 55.2, 74.8],
    },
    "CarUiRecyclerViewActivity": {
        "XML":     [25.5, 31.4, 33.4, 41.2],
        "Compose": [34.8, 51.2, 55.7, 91.6],
    },
    "GridCarUiRecyclerViewActivity": {
        "XML":     [26.8, 31.5, 33.3, 43.1],
        "Compose": [29.7, 56.7, 90.5, 128.7],
    },
    "PreferencesActivity": {
        "XML":     [23.5, 29.2, 32.0, 36.9],
        "Compose": [37.6, 51.4, 54.4, 70.3],
    },
}

frameOverrunMs = {
    "CarUiListItemActivity": {
        "XML":     [12.5, 28.7, 31.3, 947.0],
        "Compose": [17.4, 48.7, 60.9, 437.3],
    },
    "CarUiRecyclerViewActivity": {
        "XML":     [11.3, 19.5, 30.0, 915.2],
        "Compose": [31.9, 60.3, 68.1, 497.3],
    },
    "GridCarUiRecyclerViewActivity": {
        "XML":     [12.4, 19.2, 30.1, 927.1],
        "Compose": [15.4, 81.4, 143.0, 598.1],
    },
    "PreferencesActivity": {
        "XML":     [10.0, 19.0, 28.6, 912.9],
        "Compose": [35.9, 55.7, 71.0, 576.5],
    },
}

colors = {'XML': '#1565C0', 'Compose': '#2E7D32'}

def plot_subplot(metric_dict, metric_long_name):
    fig = make_subplots(
        rows=2, cols=2,
        subplot_titles=[
            f"{activity.replace('Activity',' Activity')}"
            for activity in metric_dict.keys()
        ],
        horizontal_spacing=0.12, vertical_spacing=0.15
    )

    activities = list(metric_dict.keys())
    for idx, activity in enumerate(activities):
        row = idx // 2 + 1
        col = idx % 2 + 1
        for impl in ['XML', 'Compose']:
            fig.add_trace(
                go.Scatter(
                    x=percentiles,
                    y=metric_dict[activity][impl],
                    mode='lines+markers',
                    name=impl if idx == 0 else None,
                    marker=dict(color=colors[impl], size=8),
                    line=dict(width=3, color=colors[impl]),
                    showlegend=(idx == 0),
                ),
                row=row, col=col
            )

    fig.update_layout(
        height=800,
        width=800,
        title={
            'text': f"<b>{metric_long_name} for Scroll Activities (XML vs Compose)</b>",
            'x': 0.5,
            'y': 0.96,
            'xanchor': 'center',
            'yanchor': 'top',
            'font': {'size': 18}
        },
        font=dict(size=9),
        plot_bgcolor='#EDEDED',
        paper_bgcolor='#FFFFFF',
        legend=dict(
            orientation='h',
            y=-0.08,
            x=0.5,
            xanchor='center',
            yanchor='bottom'
        ),
        margin=dict(t=90, b=80, l=60, r=60)
    )


    for anno in fig['layout']['annotations']:
        anno['font'] = dict(size=16, color='black')


    for i in range(1, 5):
        fig.update_yaxes(title_text="(ms)", row=(i-1)//2+1, col=(i-1)%2+1)

    fig.show()


plot_subplot(frameDurationCpuMs, "Frame CPU Duration")


plot_subplot(frameOverrunMs, "Frame Overrun")
