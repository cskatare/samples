/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.person;

import java.util.List;

import org.apache.http.protocol.BasicHttpContext;

import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.SortCriteria;
import com.covisint.platform.sample.httpsdk.ServiceUrl;
import com.covisint.platform.user.client.person.invitation.PersonInvitationClient;
import com.covisint.platform.user.client.person.invitation.PersonInvitationSDK;
import com.covisint.platform.user.core.person.invitation.PersonInvitation;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public final class PersonInvitationSDKSamples {

    private static PersonInvitationClient createPersonInvitationClient() {
        return new PersonInvitationSDK(ServiceUrl.PERSON_V1.getValue()).create();
    }

    /** Performs CRUD (and search) operations on person invitation resources. */
    public static void personInvitationCRUD() {

        // Create a new instance of the client.
        PersonInvitationClient client = createPersonInvitationClient();

        // Build the invitation object.
        PersonInvitation invite = new PersonInvitation();
        invite.setEmail("joe@covisint.com");
        invite.setTargetOrganizationId("5b128ea0933b");
        invite.setInvitorId("5752da251c36");
        invite.setInviteeId("fe5ea256148a");

        // Create the person invitation.
        PersonInvitation invitation = client.add(invite, new BasicHttpContext()).checkedGet();

        System.out.println("Created invitation id " + invitation.getId());

        // Retrieve an existing person invitation.

        // The invitation id.
        String inviteId = "a5be357f1a45";

        // Retrieve the person invitation.
        invitation = client.get(inviteId, new BasicHttpContext()).checkedGet();

        System.out.println("Retrieved invitation: " + invitation.toString());

        // Search person invitations.

        // The invitee id.
        String inviteeId = "86268197c07b";

        // Set up filter criteria.
        Multimap<String, String> filter = ArrayListMultimap.<String, String> create();
        filter.put("inviteeId", inviteeId);

        // Execute the search.
        List<PersonInvitation> results = client.search(filter, SortCriteria.NONE, Page.DEFAULT, new BasicHttpContext())
                .checkedGet();

        System.out.println("Search result size: " + results.size());

        // Delete our invitation.
        client.delete(inviteId, new BasicHttpContext()).checkedGet();

        System.out.println("Deleted person invitation " + inviteId);
    }

    /** Accepts a person invitation. */
    public static void acceptPersonInvitation() {

        // Create a new instance of the client.
        PersonInvitationClient client = createPersonInvitationClient();

        // The invitation id to delete.
        String inviteId = "539c68c39a50";

        // The invitee id.
        String inviteeId = "a2937a4c8ad8";

        // Process the accept task.
        client.accept(inviteId, inviteeId, new BasicHttpContext()).checkedGet();

        System.out.println("Accepted person invitation " + inviteId);
    }

}
