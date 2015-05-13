# Bitcoin node block explorer

Simple and pure block explorer you can run on top of a full node.

This block explorer patches into your Bitcoin Core node's JSON-RPC interface to retrieve transaction and block information.

It runs as a simple web application you can run on any J2EE Web Container (Jetty, Tomcat, etc.), point it toward your node and you're good to go. (Note: it also supports running on top of Blockr's API and maybe some others later on)

This block explorer remains pure to the blockchain, this means it is not dependent on any source of data other than the blockchain. Bitcoin amount values will not be displayed in fiat, transaction or block receive times do not exist.

In this initial release, even addressess are not recognized, nor can a balance be derived for them (without heaps of effort). Addresses may be integral to bitcoin, but the blockchain itself does not have any knowledge of them, they are not included for this reason.

Future releases likely will contain transaction heuristics such as that, but how this will be implemented unobtrusively is still in a grey area.

# Features

#### Block viewer

Displays all available block information. Includes an interactive hex viewer which displays the meaning of every last byte in the headers, aswell as the coinbase transaction.

#### Transaction viewer

Displays all available transaction information. Like in the block viewer, displays the meaning of every single byte in a raw transaction through an interactive hex viewer.

#### Universal search

Input anything, transaction ID, block height, block hash, raw blocks, raw transactions, and this explorer will figure out what you mean and display what's appropriate.

#### Mining simulator

Constructs a raw block which, if valid, would be accepted by the whole of the network. Visualizes what happens when you're mining, increases the nonce/extranonce, updates the timestamp and computes the block hash. Allows you to control the whole thing.

A sneak-preview client-only version of this is available at http://jornc.github.io/bitcoin-transaction-explorer/

This preview also includes the block and transaction hex viewers with contextual information for each field.

#### Script viewer (Under construction)

Visualizes bitcoin script interpretation in a step-by-step basis.

#### Raw transaction interpreter

Insert any raw transaction and this explorer will display em like it would any other transaction.

#### Raw block interpreter

Insert any raw block and --- see above.

#### Raw script interpreter (Under construction) 

See above.

# How to run it

This project is currently hosted on top of the following 2 nodes:

http://srv1.yogh.io

http://srv2.yogh.io

The former will closely follow the master branch, and will likely not always be as stable / contain bugs, but contain the latest changes.

The latter remains conservative and lags at stable releases.

You can run it yourself (encouraged! let me know!) if you have a fully built .war file of this project (see below), simply deploying it into any J2EE web container will suffice.

If you don't have a full node you can connect to Blockr (which is default if unconfigured), if you do the node needs to be fully indexed (txindex=1) and act as a JSON-RPC server (server=1).

# How to build it

- [clone the repository]

- > mvn install

- Find the .war file in /bitcoin-transactions-server/target/

- deploy the (extracted) .war file to a J2EE web container (jetty, tomcat, etc.)

- done

# Configuration

- Navigate to the web application

- Enter 'config' in the search field, hit enter

- You'll figure it out from there

# Implementation

The web application is a GWT (Google Web Toolkit) project that's patched into, essentially, a JSON-RPC proxy for a Bitcoin Core node. The proxy can be configured to reach out to Blockr's API (and others) if you aren't running a node, although it's always nice to use your own node instead.

The proxy will only request the node's getblock, getblockhash, getbestblockhash and getrawtransaction methods. It constructs (if needed) and forwards raw transaction and block data which will be interpreted locally on the client (in the browser).

It'll run in any J2EE web container when built as a .war file.
