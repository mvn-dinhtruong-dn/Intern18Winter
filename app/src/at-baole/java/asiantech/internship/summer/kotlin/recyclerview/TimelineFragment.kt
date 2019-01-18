package asiantech.internship.summer.kotlin.recyclerview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import asiantech.internship.summer.R
import asiantech.internship.summer.kotlin.recyclerview.model.TimelineItem1
import java.util.*

class TimelineFragment : Fragment(), RecyclerViewAdapter.OnItemListener {

    private var mSumItem: Int = 0
    private val mCountLike: Int = 0
    private var mCurrentItem: Int = 0
    private var mScrolledItem: Int = 0
    private val mTotalItem: Int = 10
    private val mProgressBarLoading: ProgressBar? = null
    private var isScrolled = true
    private lateinit var mTimelineItems: List<TimelineItem1>
    private var mRecyclerView: RecyclerView? = null
    private lateinit var mAdapter: RecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val recyclerView: View = inflater.inflate(R.layout.kotlin_fragment_timeline, container, false)
        mRecyclerView = recyclerView.findViewById(R.id.recyclerView)
        initView()
        return recyclerView
    }

    fun initView() {
        mRecyclerView?.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mRecyclerView?.layoutManager = linearLayoutManager
        setData()
        mAdapter = RecyclerViewAdapter(mTimelineItems)
        mRecyclerView?.adapter = mAdapter

        mRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mCurrentItem = linearLayoutManager.childCount
                mSumItem = linearLayoutManager.itemCount
                mScrolledItem = linearLayoutManager.findFirstVisibleItemPosition()
                if (isScrolled && mScrolledItem + mCurrentItem == mSumItem) {
                    isScrolled = false
                    loadMoreData()
                }
            }
        })
    }

    private fun setData() {
        mTimelineItems = ArrayList()
        for (i in 0 until mTotalItem) {
            (mTimelineItems as ArrayList<TimelineItem1>).add(TimelineItem1(R.drawable.img_avatar_1, R.drawable.img_food_1,
                    mCountLike, getString(R.string.username), getString(R.string.username), getString(R.string.comment), false))
        }
    }

    override fun onClickLike(position: Int) {
        Toast.makeText(context, R.string.liked, Toast.LENGTH_LONG).show()
        mTimelineItems[position].countLike += 1
        mAdapter.notifyDataSetChanged()
    }

    private fun loadMoreData() {
        mProgressBarLoading?.visibility = View.VISIBLE
        Thread {
            try {
                Thread.sleep(2000)
                for (i in mSumItem until mSumItem + mTotalItem) {
                    (mTimelineItems as ArrayList<TimelineItem1>).add(TimelineItem1(R.drawable.img_avatar_1, R.drawable.img_food_1,
                            mCountLike, getString(R.string.username), getString(R.string.username), getString(R.string.comment), false))
                }
                Handler(Looper.getMainLooper()).post {
                    isScrolled = true
                    mProgressBarLoading?.visibility = View.GONE
                    mRecyclerView?.adapter?.notifyDataSetChanged()
                }
            } catch (ignored: InterruptedException) {
            }
        }.start()
    }

    private fun getAvatarImageId(): Int {
        val random = Random()
        val index = random.nextInt(10)
        when (index) {
            1 -> return R.drawable.img_avatar_1
            2 -> return R.drawable.img_avatar_2
            3 -> return R.drawable.img_avatar_3
            4 -> return R.drawable.img_avatar_4
            5 -> return R.drawable.img_avatar_5
            6 -> return R.drawable.img_avatar_6
            7 -> return R.drawable.img_avatar_7
            8 -> return R.drawable.img_avatar_8
            9 -> return R.drawable.img_avatar_9
            10 -> return R.drawable.img_avatar_10
        }
        return index
    }

    private fun getImageFoodId(): Int {
        val random = Random()
        val index = random.nextInt(10)
        when (index) {
            1 -> return R.drawable.img_food_1
            2 -> return R.drawable.img_food_2
            3 -> return R.drawable.img_food_3
            4 -> return R.drawable.img_food_4
            5 -> return R.drawable.img_food_5
            6 -> return R.drawable.img_food_6
            7 -> return R.drawable.img_food_7
            8 -> return R.drawable.img_food_8
            9 -> return R.drawable.img_food_9
            10 -> return R.drawable.img_food_10
        }
        return index
    }
}