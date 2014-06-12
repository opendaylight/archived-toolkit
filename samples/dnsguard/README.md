# DNS Guard for securing the Enterprise DNS

DNS in the unifying service that is used for multiple purposes,
including for security, for improving QoS treatment, for tracking user behavior.
In most enterprises, however, the local DNS server is easily bypassed by
the clients configuring a public DNS (such as the Google DNS),
directly in their device.  This leads to lack of visibility, problems
accessing local branch services, and security vulnerabilities.

## Our solution
We implement a SDN-based solution that forwards all the DNS requests to
the local DNS, without exception. We also extract relevant statistics
of the users trying to bypass the corporate DNS server. Additional
features with regards to prioritized traffic handling will also be
bundled in this solutin in the near future.
