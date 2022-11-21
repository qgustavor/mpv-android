package `is`.xyz.mpv

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.SeekBar
import android.widget.AbsSeekBar
import android.graphics.Paint

/**
 * Seek bar with dots on it on specific time / percent
 * Based on https://stackoverflow.com/a/17639132
 * Based on https://gist.github.com/Ashusolanki/5afffb840aa017a13319654cd6c923ad
 */
class SeekBarWithTicks : SeekBar {
    private var ticks: IntArray = IntArray(0)
    private val paintBefore = Paint()
    private val paintAfter = Paint()
  
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paintBefore.setARGB(255, 211, 208, 212)
        paintAfter.setARGB(255, 116, 84, 124)
    }
    
    public fun setTicks (newTicks: IntArray) {
        ticks = newTicks
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas?.let {
            val r = 4f
            val scale = (width - paddingLeft - paddingRight) / max.toFloat()
            val baseX = paddingLeft.toFloat()
            val baseY = height / 2f

            for (tick in ticks) {
                it.drawCircle(
                  baseX + scale * tick, baseY, r,
                  if (progress > tick) paintAfter else paintBefore
                )
            }
        }

        val mThumb = getThumb()
        if (mThumb != null) {
            val mThumbOffset = getThumbOffset()
            val saveCount = canvas.save()
            val mPaddingLeft = getPaddingLeft()
            val mPaddingTop = getPaddingTop()
            // Translate the padding. For the x, we need to allow the thumb to
            // draw in its extra space
            canvas.translate((mPaddingLeft - mThumbOffset).toFloat(), mPaddingTop.toFloat())
            mThumb.draw(canvas)
            canvas.restoreToCount(saveCount)
        }
    }
}
