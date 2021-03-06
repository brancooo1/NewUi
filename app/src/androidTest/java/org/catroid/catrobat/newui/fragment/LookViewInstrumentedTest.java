package org.catroid.catrobat.newui.fragment;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.ui.SpriteActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.catroid.catrobat.newui.fragment.Utils.addNewItemNamed;
import static org.catroid.catrobat.newui.fragment.Utils.checkItemNamedExists;
import static org.catroid.catrobat.newui.fragment.Utils.checkItemNamedNotExists;
import static org.catroid.catrobat.newui.fragment.Utils.selectItemNamed;

public class LookViewInstrumentedTest {
    @Rule
    public ActivityTestRule<SpriteActivity> activityRule =
            new ActivityTestRule<>(SpriteActivity.class);

    @Before
    public void navigateToTab() {
        onView(ViewMatchers.withText(R.string.tab_name_looks)).perform(click());
    }

    @Test
    public void testAddNewItem() {
        addNewItemNamed("new item");

        checkItemNamedExists("new item");
    }


    @Test
    public void testAddMultipleItems() {
        addNewItemNamed("item 1");
        addNewItemNamed("item 2");
        addNewItemNamed("item 1");

        checkItemNamedExists("item 1");
        checkItemNamedExists("item 2");
        checkItemNamedExists("item 1 1");
    }

    @Test
    public void testCopyItems() {
        addNewItemNamed("item one");
        addNewItemNamed("item two");
        addNewItemNamed("item three");

        selectItemNamed("item one");
        selectItemNamed("item two");

        onView(withId(R.id.btnCopy)).perform(click());

        // originals
        checkItemNamedExists("item one");
        checkItemNamedExists("item two");
        checkItemNamedExists("item three");

        // copies
        checkItemNamedExists("item one 1");
        checkItemNamedExists("item two 1");
        checkItemNamedNotExists("item three 1");
    }

    @Test
    public void testRemoveItems() {
        addNewItemNamed("item one");
        addNewItemNamed("item two");
        addNewItemNamed("item three");

        selectItemNamed("item one");
        selectItemNamed("item three");

        onView(withId(R.id.btnDelete)).perform(click());

        checkItemNamedNotExists("item three");
        checkItemNamedExists("item two");
        checkItemNamedNotExists("item one");
    }

    @Test
    public void testEditItems() {
        addNewItemNamed("item one");
        selectItemNamed("item one");

        onView(withId(R.id.btnEdit)).perform(click());
        onView(withId(R.id.input)).perform(typeText("another item"));
        onView(withText(R.string.dialog_rename_primary_action)).perform(click());

        checkItemNamedNotExists("item one");
        checkItemNamedExists("another item");
    }

}
