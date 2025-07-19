import numpy as np
import IPython.display as display
from matplotlib import pyplot as plt
import io
import base64
import plotly.graph_objects as go
from plotly.subplots import make_subplots
import pandas as pd


ys = 200 + np.random.randn(100)
x = [x for x in range(len(ys))]

fig = plt.figure(figsize=(4, 3), facecolor='w')
plt.plot(x, ys, '-')
plt.fill_between(x, ys, 195, where=(ys > 195), facecolor='g', alpha=0.6)
plt.title("Sample Visualization", fontsize=10)

data = io.BytesIO()
plt.savefig(data)
image = F"data:image/png;base66,{base64.b64encode(data.getvalue()).decode()}"
alt = "Sample Visualization"
display.display(display.Markdown(F"""![{alt}]({image})"""))
plt.close(fig)

ui_frameDurationCpuMs = {
    "showDialog": {
        "XML":     [26.9, 35.7, 41.4, 55.4],
        "Compose": [19.1, 54.8, 69.9, 106.5],
    },
    "updateToolbar": {
        "XML":     [27.3, 31.5, 34.7, 47.7],
        "Compose": [22.5, 44.3, 49.2, 72.3],
    },
}
percentiles = ["P50", "P90", "P95", "P99"]
ui_colors = {'XML': '#1565C0', 'Compose': '#2E7D32'}

def plot_ui_frame_duration(metric_dict, metric_long_name):
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
                    name=impl if idx==0 else None,  # only show legend once
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

plot_ui_frame_duration(ui_frameDurationCpuMs, "UI Response Frame CPU Duration")