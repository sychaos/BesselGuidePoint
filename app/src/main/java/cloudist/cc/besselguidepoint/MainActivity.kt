package cloudist.cc.besselguidepoint

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import cloudist.cc.library.GuidePoint

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val guide_point = findViewById<GuidePoint>(R.id.guide_point)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)

        button1.setOnClickListener {
            guide_point.scrollOffset(0.65f)
        }

        button2.setOnClickListener {
            guide_point.scrollIndex(guide_point.currentIndex + 1)
        }
    }
}
