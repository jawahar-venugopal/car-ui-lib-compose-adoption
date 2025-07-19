import numpy as np
import IPython.display as display
from matplotlib import pyplot as plt
import io
import base64
import plotly.graph_objects as go
from plotly.subplots import make_subplots
import pandas as pd

ui_frameOverrunMs = {
    "showDialog": {
        "XML":     [18.1, 94.6, 145.2, 206.4],
        "Compose": [15.3, 176.2, 229.8, 459.3],
    },
    "updateToolbar": {
        "XML":     [13.0, 27.3, 30.9, 914.1],
        "Compose": [11.9, 38.1, 49.1, 519.2],
    },
}
percentiles = ["P50", "P90", "P95", "P99"]
ui_colors = {'XML': '#1565C0', 'Compose': '#2E7D32'}

def plot_ui_frame_overrun(metric_dict, metric_long_name):
    fig = make_subplots(
        rows=1, cols=2,
        subplot_titles=[f"{test} Test" for test in metric_dict.keys()],
        horizontal_spacing=0.15
    )
    tests = list(metric_dict.keys())
    for idx, test in enumerate(tests):
        col = idx + 1
        for impl in ['XML', 'Compose']:
            fig.add_trace(
                go.Scatter(
                    x=percentiles,
                    y=metric_dict[test][impl],
                    mode='lines+markers',
                    name=impl if idx==0 else None,
                    marker=dict(color=ui_colors[impl], size=8),
                    line=dict(width=3, color=ui_colors[impl]),
                    showlegend=(idx==0),
                ),
                row=1, col=col
            )
    fig.update_layout(
        height=400, width=900,
        title={'text': F"<b>{metric_long_name} (XML vs Compose)</b>", 'x':0.5, 'font':{'size':18}},
        font=dict(size=10),
        plot_bgcolor='#EDEDED',
        paper_bgcolor='#FFFFFF',
        legend=dict(orientation='h', y=-0.2, x=0.5, xanchor='center', yanchor='top'),
        margin=dict(t=80, b=80, l=60, r=60)
    )
    for anno in fig['layout']['annotations']:
        anno['font'] = dict(size=16, color='black')
    for i in range(1, 3):
        fig.update_yaxes(title_text="(ms)", row=1, col=i)
    fig.show()

plot_ui_frame_overrun(ui_frameOverrunMs, "UI Response Frame Overrun Duration")
