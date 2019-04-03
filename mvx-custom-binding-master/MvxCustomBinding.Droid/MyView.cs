using Android.App;
using Android.OS;
using MvvmCross.Platforms.Android.Views;
using Android.Support.V7.App;
using Android.Runtime;
using Android.Widget;

namespace MvxCustomBinding.Droid
{
    [Activity(Label = "Custom Binding", MainLauncher = true)]
    public class MyView : MvxActivity<MyViewModel>
    {
        TextView textDebug;
        Button connectButton, startButton, stopButton;
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            SetContentView(Resource.Layout.my_view);
            textDebug = FindViewById<TextView>(Resource.Id.textDebug);
            connectButton = FindViewById<Button>(Resource.Id.connectButton);
            startButton = FindViewById<Button>(Resource.Id.startButton);
            stopButton = FindViewById<Button>(Resource.Id.stopButton);
            textDebug.Text = "App debug:\n";

            connectButton.Click += delegate
            {
                textDebug.Text = "Connecting...";
            };

            startButton.Click += delegate
            {
                textDebug.Text = "Starting";
            };

            stopButton.Click += delegate
            {
                textDebug.Text = "Stopping";
            };
        }
    }
}